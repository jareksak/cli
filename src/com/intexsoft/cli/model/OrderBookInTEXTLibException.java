package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when ordering a book
 * in the TEXT-library.
 */
public class OrderBookInTEXTLibException extends OrderBookException {
    
    private File file = null;

    //Default constructor
    public OrderBookInTEXTLibException() {}

    //Constructor can be chained
    public OrderBookInTEXTLibException(Throwable cause) {
        super(cause);
    }

    public OrderBookInTEXTLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "OrderBookInTEXTLibException. File: " + file.getPath();
        } else {
            message = "OrderBookInTEXTLibException.";
        }

        return message;
    }
}
