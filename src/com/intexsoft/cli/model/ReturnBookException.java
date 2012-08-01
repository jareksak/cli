package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when returning a book.
 */
public class ReturnBookException extends Exception {
    
    public ReturnBookException() {}

    //Constructor can be chained
    public ReturnBookException(Throwable cause) {
        super(cause);
    }
}
