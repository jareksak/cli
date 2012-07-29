package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when a program can't delete temporality file.
 */
public class DeleteFileException extends Exception {
    
    private File file = null;

    //Default constructor
    public DeleteFileException() {}

    //Constructor recording an file object
    public DeleteFileException(File file) {
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "DeleteFileException. File: " + file.getPath();
        } else {
            message = "DeleteFileException.";
        }

        return message;
    }
}
