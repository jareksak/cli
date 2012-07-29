package com.intexsoft.cli.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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
     * @throws FindBookException
     */
    public Map<String, Object> findBook(Map<String, String> parameters)
            throws FindBookException {

        List<Book> found = new ArrayList<Book>();
        List<Book> foundMissing = new ArrayList<Book>();

        for (File file: files) {

            synchronized(file) {
                try {
                    Book book = null; 

                    //throws FileNotFoundException, IOException
                    book = getBook(file);
                    
                    if (book == null) continue;
                        
                    if (book.findOnAuthorOrName(parameters)) {
                        if (book.getIssued() == null)
                            found.add((Book) book.clone());
                        else
                            foundMissing.add((Book) book.clone());
                    }
                } catch (FileNotFoundException e) {
                    throw new FindBookException(file, e);
                    
                } catch (IOException e) {
                    throw new FindBookException(file, e);
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
     * @throws OrderBookException
     */
    public Map<String, Object> orderBook(Map<String, String> parameters)
            throws OrderBookException {

        Map<String, Object> result = new HashMap<String, Object>();

        for (File file: files) {

            synchronized(file) {
                Book book = null; 
                try {
                    //throws FileNotFoundException, IOException
                    book = getBook(file);

                    if (book == null) continue;

                    if (book.findOnId(parameters)) { 
                        if (book.getAbonent() == null) {
                            book.setAbonent(parameters.get("abonent"));
                            book.setIssued(getCurrentDate());

                            //throws IOException,
                            //DeleteFileException, RenameTmpFileException
                            rewriteFile(file, book);

                            result.put("OK", (Book) book.clone());
                        } else {
                            result.put("RESERVED", (Book) book.clone());
                        }
                    } 
                } catch (FileNotFoundException e) {
                    throw new OrderBookException(file, e);
                    
                } catch (IOException e) {
                    throw new OrderBookException(file, e);

                } catch (DeleteFileException e) {
                    throw new OrderBookException(e);

                } catch (RenameTmpFileException e) {
                    throw new OrderBookException(e);
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
     * @throws ReturnBookException
     */
    public Map<String, Object> returnBook(Map<String, String> parameters)
            throws ReturnBookException {

        Map<String, Object> result = new HashMap<String, Object>();
        Boolean found = false;

        for (File file: files) {
        
            synchronized(file) {
                if (found) break;
                List<Book> books = new ArrayList<Book>();

                Book book = null; 
                try {
                    //throws FileNotFoundException, IOException
                    book = getBook(file);

                    if (book == null) continue;
                            
                    if (book.findOnId(parameters)) { 
                        if (book.getAbonent() != null) {
                            book.setIssued(null);
                            result.put("OK", (Book) book.clone());

                            book.setAbonent(null);

                            //throws IOException,
                            //DeleteFileException, RenameTmpFileException
                            rewriteFile(file, book);
                        } else {
                            result.put("ALREADYRETURNED", null);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new ReturnBookException(file, e);
                    
                } catch (IOException e) {
                    throw new ReturnBookException(file, e);

                } catch (DeleteFileException e) {
                    throw new ReturnBookException(e);

                } catch (RenameTmpFileException e) {
                    throw new ReturnBookException(e);
                }
            }
        }
               
        return result;
    }

    /**
     * Method get a file object and return a book object.
     * @param file Object of the file that contains the book fields.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private Book getBook(File file)
            throws FileNotFoundException, IOException {

        //throws FileNotFoundException
        BufferedReader in = new BufferedReader(new FileReader(file));

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
        
        //throws IOException
        if (in != null) in.close();

        return new Book(fields[0], fields[1], fields[2], fields[3], fields[4]);
    }

    /**
     * Method for rewrite a file including information about books after
     * successful order or return operation.
     *
     * @param file The File object with information about books.
     * @param book The Book object for write in the file.
     *
     * @throws IOException
     * @throws DeleteFileException
     * @throws RenameFileException
     */
    private void rewriteFile(File file, Book book)
            throws  IOException, DeleteFileException,
                    RenameTmpFileException {

        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        PrintWriter pw = null;
        
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
            throw new DeleteFileException(file);
        }

        if (!tempFile.renameTo(file))
            throw new RenameTmpFileException(tempFile);
            
        if (pw != null) pw.close();
    }
}

