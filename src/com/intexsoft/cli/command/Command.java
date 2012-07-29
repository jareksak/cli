package com.intexsoft.cli.command;

import java.util.Map;
import java.util.HashMap;

import com.intexsoft.cli.model.CLIModel;

/**
 * The abstract class for clases of commands.
*/

public abstract class Command {

    protected String commandName = "";
    protected Map parameters = new HashMap<String, String> ();
    protected CLIModel model;

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public void setModel(CLIModel model) {
        this.model = model;
    }

    public String getCommandName() {
        return commandName;
    }

    /**
     * Abstract method for executing the command.
     */
    public abstract Object execute();

    /**
     * Abstract method for validate syntax of the command.
     */
    public abstract boolean isValid();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("command:" + getCommandName());
        if (parameters != null)
            sb.append(" " + parameters.toString());
        sb.append("}");

        return sb.toString();
    }
}

