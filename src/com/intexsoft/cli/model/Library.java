package com.intexsoft.cli.model;

import java.util.List;
import java.util.Map;

import com.intexsoft.cli.exception.CLIException;

/**
 * The interfase defining methods characteristic for library classes.
 */
public interface Library {

    /**
     * @return Name of a library. 
     */
    public String getName();
    
    /**
     * The method for finding books in the library.
     *
     * @param parameters Parameters for finding books.
     *                   Expected parameters: author, name.
     * @return           A map with results.
     * @throws CLIException
     */
    public Map<String, Object> findBook(Map<String, String> parameters)
        throws CLIException;

    /**
     * The method for ordering a book in the library.
     *
     * @param parameters Parameters for ordering the book.
     *                   Expected parameters: id, abonent.
     * @return           A map with a result. 
     * @throws CLIException
     */
    public Map<String, Object> orderBook(Map<String, String> parameters)
        throws CLIException;

    /**
     * The method for returning a book to library.
     *
     * @param parameters Parameters for returning the book.
     *                   Expected parameters: id.
     * @return           A map with a result.
     * @throws CLIException
     */
    public Map<String, Object> returnBook(Map<String, String> parameters)
        throws CLIException;
}

