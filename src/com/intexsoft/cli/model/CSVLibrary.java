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
 * Implementation of the AbstractLibrary class.<br>
 *
 * Class for work with CSV-libraries.
 * In CSV-library information about books is store in files:<br>
 * A.csv, B.csv, ...<br>
 * In each file can store information abount many books.<br>
 * Example:<br>
 * <pre>
 * 1000, Asimov, Foundation, 2006.12.10, Yavorchuk
 * 1001, Bulgakov, Margarita,,
 * ...
 * </pre>
 */
public class CSVLibrary extends AbstractLibrary {

    public CSVLibrary(File dirLibrary) {
            super(dirLibrary);
    }
 
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
            throws FindBookException {

        List<Book> found = new ArrayList<Book>();
        List<Book> foundMissing = new ArrayList<Book>();

        for (File file: files) {
            
            synchronized(file) {
                BufferedReader in = null;
                try {
                    //throws FileNotFoundException
                    in = new BufferedReader(new FileReader(file));

                    String line;
                    //throws IOException
                    while ((line = in.readLine()) != null) {
                       
                        Book book = getBook(line);
                        if (book == null) continue;
                        
                        if (book.findOnAuthorOrName(parameters)) {
                            if (book.getIssued() == null)
                                found.add((Book) book.clone());
                            else
                                foundMissing.add((Book) book.clone());
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new FindBookException(file, e);
                    
                } catch (IOException e) {
                    throw new FindBookException(file, e);

                } finally {
                    try {
                        if (in != null) in.close();
                    } catch (IOException e) {
                        throw new FindBookException(file, e);
                    }
                }
            }
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        if (found.isEmpty() && foundMissing.isEmpty()) {
            result.put("NOTFOUND", null);
        } else {
            result.put("FOUND", found);
            result.put("FOUNDMISSING", foundMissing);
        }

        return result;
    }

    /**
     * Implementation of a method for order a book.
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
            throws OrderBookException {

        Map<String, Object> result = new HashMap<String, Object>();

        Boolean found = false;
        for (File file: files) {

            synchronized(file) {
                if (found) break;
                List<Book> books = new ArrayList<Book>();

                BufferedReader in = null;
                try {
                    //throws FileNotFoundException
                    in = new BufferedReader(new FileReader(file));

                    String line;
                    //throws IOException
                    while ((line = in.readLine()) != null) {

                        Book book = getBook(line);
                        if (book == null) continue;
                        
                        if (book.findOnId(parameters)) { 
                            if (book.getAbonent() == null) {
                                book.setAbonent(parameters.get("abonent"));
                                book.setIssued(getCurrentDate());
                                books.add(book);

                                found = true;

                                result.put("OK", (Book) book.clone());
                            } else {
                                books.add(book);

                                result.put("RESERVED", (Book) book.clone());
                            }
                        } else
                            books.add(book);
                    }
                    //throws FileNotFoundException, DeleteTmpFileException,
                    //RenameTmpFileException
                    if (found) rewriteFile(file, books);

                } catch (FileNotFoundException e) {
                    throw new OrderBookException(file, e);
                    
                } catch (IOException e) {
                    throw new OrderBookException(file, e);

                } catch (DeleteTmpFileException e) {
                    throw new OrderBookException(e);

                } catch (RenameTmpFileException e) {
                    throw new OrderBookException(e);

                } finally {
                    try {
                        if (in != null) in.close();
                    } catch (IOException e) {
                        throw new OrderBookException(file, e);
                    }
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
                List<Book> books = new ArrayList<Book> ();

                BufferedReader in = null;
                try {
                    //throws FileNotFoundException
                    in = new BufferedReader(new FileReader(file));

                    String line;
                    //throws IOException
                    while ((line = in.readLine()) != null) {

                        Book book = getBook(line);
                        if (book == null) continue;
                        
                        if (book.findOnId(parameters)) { 
                            if (book.getAbonent() != null) {
                                book.setIssued(null);
                                result.put("OK", (Book) book.clone());

                                book.setAbonent("");
                                books.add(book);

                                found = true;
                            } else {
                                books.add(book);

                                result.put("ALREADYRETURNED", null);
                            }
                        } else
                            books.add(book);
                    }

                    //throws FileNotFoundException, DeleteTmpFileException,
                    //RenameTmpFileException
                    if (found) rewriteFile(file, books);

                } catch (FileNotFoundException e) {
                    throw new OrderBookException(file, e);
                    
                } catch (IOException e) {
                    throw new OrderBookException(file, e);

                } catch (DeleteTmpFileException e) {
                    throw new OrderBookException(e);

                } catch (RenameTmpFileException e) {
                    throw new OrderBookException(e);

                } finally {
                    try {
                        if (in != null) in.close();
                    } catch (IOException e) {
                        throw new OrderBookException(file, e);
                    }
                }
            }
        }
                
        return result;
    }

    /**
     * Method get a line from a file and return a book object.
     * @param line Line that was reading from the file.
     */
    private Book getBook(String line) {
   
        Book book = null;
        String[] fields = line.split(",");
        if (fields.length == 3) {
            book = new Book(fields[0], fields[1], fields[2]);
        } else if (fields.length == 5) {
            book = new Book(fields[0], fields[1], fields[2],
                            fields[3], fields[4]);
        }
        return book;
    }

    /**
     * Method for rewrite the file including information about books after
     * successful order or return operation.
     *
     * @param file  File object with information about books.
     * @param books List objects type of Book for write in the file.
     * 
     * @throws CLIException
     */
    private void rewriteFile(File file, List<Book> books)
            throws FileNotFoundException, DeleteTmpFileException, RenameTmpFileException {

        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        PrintWriter pw = null;
            
        //throws FileNotFoundException
        pw = new PrintWriter(new FileWriter(tempFile));
        
        for (Book book: books) {
            StringBuilder sb = new StringBuilder();
            sb.append(book.getId() + ",");
            sb.append(book.getAuthor() + ",");
            sb.append(book.getName() + ",");
            if (book.getIssued() != null &&
                book.getAbonent() != null) {
            
                sb.append(book.getIssued() + ",");
                sb.append(book.getAbonent());
            } else    
                sb.append(",");

            pw.println(sb.toString());
            pw.flush();
        }

        if (!file.delete()) {
            throw new DeleteTmpFileException(file);
        }

        if (!tempFile.renameTo(file))
            throw new RenameTmpFileException(file);
            
        if (in != null) in.close();
    }
}

