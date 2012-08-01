package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when finding a book.
 */
public class FindBookException extends Exception {
    
    //Default constructor
    public FindBookException() {}

    //Constructor can be chained
    public FindBookException(Throwable cause) {
        super(cause);
    }
}
