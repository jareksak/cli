package com.intexsoft.cli.controller;

import java.util.Map;
import com.intexsoft.cli.model.CLIModel;

/**
 * The class of the command for returning the book.
 */
public class ReturnCommand extends Command {

    /**
     * Implementation of the abstract method for executing the command.
     */
    public Map execute() throws ReturnCommandException {
        return model.returnBook(parameters); 
    }

    /**
     * Implementation of the method for validate syntax of the command.<br>
     * Command format:<br>
     * <pre>
     * RETURN id=&lt;index&gt;
     * </pre>
     */
    public boolean isValid() {
        Boolean valid = false;
        
        if (parameters.size() == 1 &&
                parameters.get("id") != null)
            valid = true;

        return valid;
    }
}

