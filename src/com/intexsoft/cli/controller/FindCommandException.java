package com.intexsoft.cli.controller;

import java.io.File;

import com.intexsoft.cli.model.Library;

/**
 * The exception throws when occured an error when executing
 * the find command.
 */
public class FindCommandException extends CommandException {
    
    private Library library = null;

    //Default constructor
    public FindCommandException() {}

    //Constructor recording an file object & can be chained
    public FindCommandException(Library library, Throwable cause) {
        super(cause);
        this.library = library;
    }
    
    private Boolean isSetLibrary() {
        return (library != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetLibrary()) {
            message = "FindCommandException. Library: " + library.getName();
        } else {
            message = "FindCommandException.";
        }

        return message;
    }
}
