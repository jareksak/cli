package com.intexsoft.cli.controller;

import java.io.File;

import com.intexsoft.cli.model.Library;

/**
 * The exception throws when occured an error when executing
 * the order command.
 */
public class OrderCommandException extends CommandException {
    
    private Library library = null;

    //Default constructor
    public OrderCommandException() {}

    //Constructor recording an file object & can be chained
    public OrderCommandException(Library library, Throwable cause) {
        super(cause);
        this.library = library;
    }
    
    private Boolean isSetLibrary() {
        return (library != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetLibrary()) {
            message = "OrderCommandException. Library: " + library.getName();
        } else {
            message = "OrderCommandException.";
        }

        return message;
    }
}
