package com.intexsoft.cli.command;

/**
 * The class of the command for getting help about syntax of commands.
 */
public class HelpCommand extends Command {

    /**
     * Implementation of the abstract method for executing the command.
     */
    public String execute() {
        String help =
        "Commands:\n" +
        "FIND [author=<author>] [name=<name of a book>]\n" +
        "ORDER id=<index> abonent=<abonent name>\n" +
        "RETURN id=<index>\n" +
        "HELP";

        return help;
    }

    public boolean isValid() {
        return true;
    }
}

