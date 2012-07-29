package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when returning a book.
 */
public class ReturnBookException extends Exception {
    
    private File file = null;

    //Default constructor
    public ReturnBookException() {}

    //Constructor can be chained
    public ReturnBookException(Throwable cause) {
        super(cause);
    }

    //Constructor recording an file object & can be chained
    public ReturnBookException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "ReturnBookException. File: " + file.getPath();
        } else {
            message = "ReturnBookException.";
        }

        return message;
    }
}
