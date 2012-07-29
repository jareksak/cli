package com.intexsoft.cli.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;

/**
 * The abstract class for library classes.
 */
public abstract class AbstractLibrary implements Library {

    /** A library name (name the directory that containing
     * files with information about books).*/
    private String nameLibrary;

    /** List of the File objects representing files in the library.*/
    protected List<File> files = new ArrayList<File> ();

    /**
     * @param dirLibrary A directory that containing files with information
     *                   about books.
     */
    public AbstractLibrary(File dirLibrary) {

        this.nameLibrary = dirLibrary.getName();
        files = Arrays.asList(dirLibrary.listFiles());
    }

    /**
     * Abstract method for finding books.
     *
     * @param parameters Parameters for finding books.
     * @return           List of objects with find results.
     *
     * @throws FindBookException
     */
    public abstract Map<String, Object> findBook(Map<String, String> parameters)
        throws FindBookException;

    /**
     * Abstract method for order a book.
     *
     * @param parameters Parameters for ordering the book.
     * @return           Object with result the operation.
     *
     * @throws OrderBookException
     */
    public abstract Map<String, Object> orderBook(Map<String, String> parameters)
        throws OrderBookException;

    /**
     * Abstract method for return a book.
     *
     * @param parameters Parameters for returning the book.
     * @return           Object with result the operation.
     *
     * @throws ReturnBookException
     */
    public abstract Map<String, Object> returnBook(Map<String, String> parameters)
        throws ReturnBookException;

    public String getName() {
        return nameLibrary;
    }

    protected Date getCurrentDate() {
        return new Date();
    }
}

