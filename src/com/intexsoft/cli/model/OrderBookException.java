package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when occured an error when ordering a book.
 */
public class OrderBookException extends Exception {
    
    //Default constructor
    public OrderBookException() {}

    //Constructor can be chained
    public OrderBookException(Throwable cause) {
        super(cause);
    }
}
