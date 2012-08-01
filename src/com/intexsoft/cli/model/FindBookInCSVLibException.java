package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when finding a book
 * in the CSV-library.
 */
public class FindBookInCSVLibException extends FindBookException {
    
    private File file = null;

    //Default constructor
    public FindBookInCSVLibException() {}

    //Constructor recording an file object & can be chained
    public FindBookInCSVLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "FindBookInCSVLibException. File: " + file.getPath();
        } else {
            message = "FindBookInCSVLibException.";
        }

        return message;
    }
}
