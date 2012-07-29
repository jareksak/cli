package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when finding a book.
 */
public class FindBookException extends Exception {
    
    private File file = null;

    //Default constructor
    public FindBookException() {}

    //Constructor recording an file object & can be chained
    public FindBookException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "FindBookException. File: " + file.getPath();
        } else {
            message = "FindBookException.";
        }

        return message;
    }
}
