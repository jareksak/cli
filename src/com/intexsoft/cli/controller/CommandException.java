package com.intexsoft.cli.controller;

import java.io.File;

/**
 * The exception throws when occured an error when executing a command.
 */
public class CommandException extends Exception {
    
    //Default constructor
    public CommandException() {}

    //Constructor can be chained
    public CommandException(Throwable cause) {
        super(cause);
    }
}
