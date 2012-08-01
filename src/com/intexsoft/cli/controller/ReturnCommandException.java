package com.intexsoft.cli.controller;

import java.io.File;

import com.intexsoft.cli.model.Library;

/**
 * The exception throws when occured an error when executing
 * the return command.
 */
public class ReturnCommandException extends CommandException {
    
    private Library library = null;

    //Default constructor
    public ReturnCommandException() {}

    //Constructor recording an file object & can be chained
    public ReturnCommandException(Library library, Throwable cause) {
        super(cause);
        this.library = library;
    }
    
    private Boolean isSetLibrary() {
        return (library != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetLibrary()) {
            message = "ReturnCommandException. Library: " + library.getName();
        } else {
            message = "ReturnCommandException.";
        }

        return message;
    }
}
