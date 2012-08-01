package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when ordering a book
 * in the CSV-library.
 */
public class OrderBookInCSVLibException extends OrderBookException {
    
    private File file = null;

    //Default constructor
    public OrderBookInCSVLibException() {}

    //Constructor can be chained
    public OrderBookInCSVLibException(Throwable cause) {
        super(cause);
    }

    public OrderBookInCSVLibException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "OrderBookInCSVLibException. File: " + file.getPath();
        } else {
            message = "OrderBookInCSVLibException.";
        }

        return message;
    }
}
