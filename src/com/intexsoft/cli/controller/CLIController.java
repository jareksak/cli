package com.intexsoft.cli.controller;

import java.io.StringWriter;
import java.io.PrintWriter;

import com.intexsoft.cli.view.CLIView;
import com.intexsoft.cli.model.CLIModel;

import org.apache.log4j.Logger;

/**
 * The class of controller.<br>
 *
 * The class receive text of the inputed command from a view.
 * Create object of a command ant execute the command.
 * If the command object is null send to the view the string
 * "SYNTAXERROR".<br>
 */
public class CLIController {

    private static Logger log = Logger.getLogger(CLIController.class);

    private CLIModel model;

    public CLIController(CLIModel model) {
        this.model = model;
    }

    /**
     * The method receive from the view text of inputed command and initiating
     * execution the command.
     *
     * @param view The CLIView object.
     */
    public void update(CLIView view) {
        String expression = view.getExpression();

        if (log.isDebugEnabled()) {
            log.debug("Controller got expression: " + expression);
        }
        
        CLICommandFactory factory = new CLICommandFactory();
        Command command = factory.getCommand(expression, model);
        
        if (log.isDebugEnabled()) {
            log.debug("Created the command object: " + command);
        }
        
        Object result;
        if (command != null) {
            try {
                result = command.execute();
            } catch (CommandException e) {
                StringWriter trace = new StringWriter();
                e.printStackTrace(new PrintWriter(trace));
                log.error(trace.toString());
                result = "Error when command executing.\n" +
                    "Details in the log file.\n" +
                    "Try again.";
            }
        } else {
            result = "SYNTAXERROR";
        }

        view.update(result);
    }
}

