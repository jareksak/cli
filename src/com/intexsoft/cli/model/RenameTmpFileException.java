package com.intexsoft.cli.model;

import java.io.File;

/**
 * The exception throws when a program can't rename temporality file.
 */
public class RenameTmpFileException extends Exception {
    
    private File file = null;

    //Default constructor
    public RenameTmpFileException() {}

    //Constructor recording an file object
    public RenameTmpFileException(File file) {
        this.file = file;
    }
    
    private Boolean isSetFile() {
        return (file != null) ? true : false;
    }

    public String toString() {
        String message;
        if (isSetFile()) {
            message = "RenameTmpFileException. File: " + file.getPath();
        } else {
            message = "RenameTmpFileException.";
        }

        return message;
    }
}
