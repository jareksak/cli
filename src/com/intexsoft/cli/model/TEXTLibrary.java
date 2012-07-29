package com.intexsoft.cli.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.File;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.intexsoft.cli.exception.CLIException;

/**
 * Implementation the abstract class AbstractLibrary.<br>
 *
 * Class for work with TEXT-libraries.
 * In TEXT-library information about books witch stores in files:<br>
 * 01.properties, 02.properties, ...<br>
 * In each file can store information abount only one a book.<br>
 * Example:<br>
 * <pre>
 * Index=1000
 * Author=Asimov
 * Name=Foundation
 * Issued=2006.12.10
 * Issuedto=Yavorchuk
 * </pre>
 */
public class TEXTLibrary extends AbstractLibrary {
    
    public TEXTLibrary(File dirLibrary) { super(dirLibrary); }

    /**
     * Implementation of method for finding books.
     * Method put to result Map following elements:
     * <pre>
     * Keys:         Types of values:
     * FOUND         List&lt;Book&gt;
     * FOUNDMISSING  List&lt;Book&gt;
     *
     * or
     *
     * NOTFOUND      null 
     * </pre>
     *
     * @param parameters Parameters for finding books.
     * @return A Map&lt;String, Object&gt; object.
     *
     * @throws CLIException
     */
    public Map<String, Object> findBook(Map<String, String> parameters)
            throws CLIException {

        List<Book> found = new ArrayList<Book>();
        List<Book> foundMissing = new ArrayList<Book>();

        for (File file: files) {

            synchronized(file) {
                try {
                    Book book = null; 

                    //throws CLIException
                    book = getBook(file);
                    
                    if (book == null) continue;
                        
                    if (book.findOnAuthorOrName(parameters)) {
                        if (book.getIssued() == null)
                            found.add((Book) book.clone());
                        else
                            foundMissing.add((Book) book.clone());
                    }
                } catch (CLIException e) {
                    throw e;
                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        if (found.isEmpty() && foundMissing.isEmpty()) {
            result.put("NOTFOUND", true);
        } else {
            result.put("FOUND", found);
            result.put("FOUNDMISSING", foundMissing);
        }

        return result;
    }

    /**
     * Implementation of method for order the book.
     * Method put to result Map following elements:
     * <pre>
     * Keys:    Types of values:
     * OK       Book
     *
     * or
     *
     * RESERVED Book
     * </pre>
     *
     * @param parameters Parameters for ordering the book.
     * @return A Map&lt;String, Object&gt; object.
     *
     * @throws CLIException
     */
    public Map<String, Object> orderBook(Map<String, String> parameters)
            throws CLIException {

        Map<String, Object> result = new HashMap<String, Object>();

        for (File file: files) {

            synchronized(file) {
                Book book = null; 
                try {
                    //throws CLIException
                    book = getBook(file);

                    if (book == null) continue;

                    if (book.findOnId(parameters)) { 
                        if (book.getAbonent() == null) {
                            book.setAbonent(parameters.get("abonent"));
                            book.setIssued(getCurrentDate());
                            rewriteFile(file, book);

                            result.put("OK", (Book) book.clone());
                        } else {
                            result.put("RESERVED", (Book) book.clone());
                        }
                    } 

                } catch (CLIException e) {
                    throw e;
                }
            }
        }

        return result;
    }

    /**
     * Implementation of method for return the book.
     * Method put to result Map following elements:
     * <pre>
     * Keys:           Types of values:
     * OK              Book
     *
     * or
     *
     * ALREADYRETURNED Book 
     * </pre>
     *
     * @param parameters Parameters for returning the book.
     * @return A Map&lt;String, Object&gt; object.
     *
     * @throws CLIException
     */
    public Map<String, Object> returnBook(Map<String, String> parameters)
            throws CLIException {

        Map<String, Object> result = new HashMap<String, Object>();
        Boolean found = false;

        for (File file: files) {
        
            synchronized(file) {
                if (found) break;
                List<Book> books = new ArrayList<Book>();

                Book book = null; 
                try {
                    //throws CLIException
                    book = getBook(file);

                    if (book == null) continue;
                            
                    if (book.findOnId(parameters)) { 
                        if (book.getAbonent() != null) {
                            book.setIssued(null);
                            result.put("OK", (Book) book.clone());

                            book.setAbonent(null);
                            //throws CLIException
                            rewriteFile(file, book);
                        } else {
                            result.put("ALREADYRETURNED", null);
                        }
                    }

                } catch (CLIException e) {
                    throw e;
                }
            }
        }
               
        return result;
    }

    /**
     * Method get a file object and return a book object.
     * @param file Object of the file that contains the book fields.
     *
     * @throws CLIException
     */
    private Book getBook(File file) throws CLIException {

        Book book = null;
        LineNumberReader in = null;
        try {
            //throws FileNotFoundException
            in = new LineNumberReader(new FileReader(file));

            String[] fields = new String[5];

            String line;
            int i = 0;
            //throws IOException
            while ((line = in.readLine()) != null) {
                StringTokenizer sTokenizer = new StringTokenizer(line, "=");
                sTokenizer.nextToken();
                if (sTokenizer.hasMoreTokens()) {
                    fields[i] = sTokenizer.nextToken();
                    i++;
                }
            }

            book = new Book(fields[0], fields[1], fields[2],
                                 fields[3], fields[4]);
        
        } catch (FileNotFoundException e) {
            throw new CLIException("Can't open file: " +
                file.getPath() + ". ");
        } catch (IOException e) {
            throw new CLIException("Can't read line: " +
                in.getLineNumber() + " in file: " + file + ". ");
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                throw new CLIException("Can't close file: " +
                    file.getPath() + ". ");
            }
        }

        return book;
    }

    /**
     * Method for rewrite a file including information about books after
     * successful order or return operation.
     *
     * @param file The File object with information about books.
     * @param book The Book object for write in the file.
     *
     * @throws CLIException
     */
    private void rewriteFile(File file, Book book) throws CLIException {

        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        PrintWriter pw = null;
        
        try {
            //throws IOException
            pw = new PrintWriter(new FileWriter(tempFile));

            StringBuilder sb = new StringBuilder();
            sb.append("Index=" + book.getId() + "\n");
            sb.append("Author=" + book.getAuthor() + "\n");
            sb.append("Name=" + book.getName() + "\n");
            if (book.getIssued() != null)
                sb.append("Issued=" + book.getIssued() + "\n");
            else
                sb.append("Issued=\n");
            if (book.getAbonent() != null)
                sb.append("Issuedto=" + book.getAbonent());
            else
                sb.append("Issuedto=\n");

            pw.print(sb.toString());
            pw.flush();

            if (!file.delete()) {
                throw new CLIException("Can't delete file " +
                                        file.getName() + ". ");
            }

            if (!tempFile.renameTo(file))
                throw new CLIException("Can't rename file" +
                                        tempFile.getName() + ". ");

        } catch (IOException e) {
            throw new CLIException("Can't open file: " + tempFile + ". ");
        } catch (CLIException e) {
            throw e;
        } finally {
            if (pw != null) pw.close();
        }
    }
}

