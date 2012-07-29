package com.intexsoft.cli.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.intexsoft.cli.exception.CLIException;

import org.apache.log4j.Logger;

/**
 * The class represent a model of the Central Library Index (CLI).<br>
 */
public class CLIModel {

    private static Logger log = Logger.getLogger(CLIModel.class);

    /** A list of library objects.*/
    private List<Library> libraries = new ArrayList<Library>();

    /**
     * Scaning the rootDirectory, creating Library objects and
     * filling the libraries list.
     *
     * @param rootDirectory The root CLI directory
     */
    public CLIModel(String rootDirectory) {
    
        File dirLibrary = new File(rootDirectory);
        scanDirectory(dirLibrary); 
    }

    private void scanDirectory(File dirLibrary) {

        File[] files = dirLibrary.listFiles();

        if (files != null) {
            for (File file : Arrays.asList(files)) {
                if (file.isDirectory()) {
                    if (file.getName().indexOf("CSV_") != -1) {
                        libraries.add(new CSVLibrary(file));
                    } else if (file.getName().indexOf("Text_") != -1) {
                        libraries.add(new TEXTLibrary(file));
                    }
                }
            }
        }
    }
   
    /**
     * Method to finding books in the CLI.
     * Result list has Map elements with keys:
     * <pre>
     * Key:          Type of value:
     * FOUND         List&lt;Book&gt;
     * FOUNDMISSING  List&lt;Book&gt;
     * LIBNAME       String
     * </pre>
     *
     * @param parameters Parameters for finding books.
     */ 
    public List findBook(Map<String, String> parameters) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Boolean found = false;
        for (Library library: libraries) {
            try {
                //throws CLIException
                Map<String, Object> findResult = library.findBook(parameters);

                if (!findResult.containsKey("NOTFOUND")) {
                    found = true;
                    findResult.put("LIBNAME", library.getName());
                    result.add(findResult);
                }

            } catch (CLIException e) {
                log.error("Error in findBook(). Library: " +
                           library.getName() + ". " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Method for ordering the book in the CLI.
     * Result has Map with keys:
     * <pre>
     * Keys:    Types of values:
     * COMMAND  String
     * 
     * OK       Book
     * or
     * RESERVED Book
     * </pre>
     *
     * @param parameters for ordering the book.
     */
    public Map<String, Object>
            orderBook(Map<String, String> parameters) {
        
        Map<String, Object> result = null; 
        Boolean found = false;
        for (Library library: libraries) {
            try {
                //throws CLIException
                result = library.orderBook(parameters);

                if (!result.isEmpty()) {
                    found = true;
                    result.put("COMMAND", "order");
                    break;
                }
                
            } catch (CLIException e) {
                log.error("Error in orderBook(). Library: " +
                           library.getName() + ". " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Method for returning the book in the CLI.
     * Result has Map with keys:
     * <pre>
     * Keys:           Types of values:
     * COMMAND  String
     *
     * OK              Book
     * or
     * ALREADYRETURNED Book 
     * </pre>
     *
     * @param parameters Parameters for returning the book.
     */
    public Map<String, Object> returnBook(Map<String, String> parameters) {

        Map<String, Object> result = null;
        Boolean found = false;
        for (Library library: libraries) {
            try {
                result = library.returnBook(parameters);
                
                if (!result.isEmpty()) {
                    found = true;
                    result.put("COMMAND", "return");
                    break;
                }

            } catch (CLIException e) {
                log.error("Error in returnBook(). Library: " +
                           library.getName() + ". " + e.getMessage());
            }
        } 

        return result;
    }
}

