package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when returning a book
 * in the TEXT-library.
 */
public class ReturnBookInTEXTLibException extends ReturnBookException {
    
    private File file = null;

    //Default constructor
    public ReturnBookInTEXTLibException() {}

    //Constructor can be chained
    public ReturnBookInTEXTLibException(Throwable cause) {
        super(cause);
    }

    //Constructor recording an file object & can be chained
    public ReturnBookInTEXTLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "ReturnBookInTEXTLibException. File: " + file.getPath();
        } else {
            message = "ReturnBookInTEXTLibException.";
        }

        return message;
    }
}
