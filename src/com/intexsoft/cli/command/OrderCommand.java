package com.intexsoft.cli.command;

import java.util.Map;
import com.intexsoft.cli.model.CLIModel;

/**
 * The class of the command for ordering the book.
 */
public class OrderCommand extends Command {
    
    /**
     * Implementation of the abstract method for executing the command.
     */
    public Map execute() {
        return model.orderBook(parameters); 
    }

    /**
     * Implementation of the method for validate syntax of the command.<br>
     * Command format:<br>
     * <pre>
     * ORDER id=&lt;index&gt; abonent=&lt;abonent&gt;
     * </pre>
     */
    public boolean isValid() {
        Boolean valid = false;
        if (parameters.size() != 2) return false;

        if (parameters.get("id") != null && 
            parameters.get("abonent") != null)
            valid = true;

        return valid;
    }
}

