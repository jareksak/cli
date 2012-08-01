package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when finding a book
 * in the TEXT-library.
 */
public class FindBookInTEXTLibException extends FindBookException {
    
    private File file = null;

    //Default constructor
    public FindBookInTEXTLibException() {}

    //Constructor recording an file object & can be chained
    public FindBookInTEXTLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "FindBookInTEXTLibException. File: " + file.getPath();
        } else {
            message = "FindBookInTEXTLibException.";
        }

        return message;
    }
}
