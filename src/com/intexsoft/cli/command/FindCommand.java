package com.intexsoft.cli.command;

import java.util.Map;
import java.util.List;

import com.intexsoft.cli.model.CLIModel;

/**
 * The class of the command for finding books.
 */
public class FindCommand extends Command {

    /**
     * Implementation of the abstract method for executing the command.
     */
    public List execute() {
        return model.findBook(parameters); 
    }

    /**
     * Implementation of the abstract method for validate syntax of the command.<br>
     * Command format:<br>
     * <pre>
     * FIND [author=&lt;author name&gt;] [name=&lt;book name&gt;]
     * </pre>
     */
    public boolean isValid() {
        Boolean valid = false;

        switch (parameters.size()) {
        case 0:
            valid = true;
            break;

        case 1:
            if (parameters.get("author") != null ||
                    parameters.get("name") != null)
                valid = true;
            break;

        case 2:
            if (parameters.get("author") != null &&
                    parameters.get("name") != null)
                valid = true;
            break;
        }

        return valid;
    }
}

