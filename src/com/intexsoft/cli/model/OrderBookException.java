package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when ordering a book.
 */
public class OrderBookException extends Exception {
    
    private File file = null;

    //Default constructor
    public OrderBookException() {}

    //Constructor can be chained
    public OrderBookException(Throwable cause) {
        super(cause);
    }

    //Constructor recording an file object & can be chained
    public OrderBookException(File file, Throwable cause) {
        super(cause);
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "OrderBookException. File: " + file.getPath();
        } else {
            message = "OrderBookException.";
        }

        return message;
    }
}
