package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when returning a book
 * in the CSV-library.
 */
public class ReturnBookInCSVLibException extends ReturnBookException {
    
    private File file = null;

    //Default constructor
    public ReturnBookInCSVLibException() {}

    //Constructor can be chained
    public ReturnBookInCSVLibException(Throwable cause) {
        super(cause);
    }

    //Constructor recording an file object & can be chained
    public ReturnBookInCSVLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "ReturnBookInCSVLibException. File: " + file.getPath();
        } else {
            message = "ReturnBookInCSVLibException.";
        }

        return message;
    }
}
