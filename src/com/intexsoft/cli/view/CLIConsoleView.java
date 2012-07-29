package com.intexsoft.cli.view;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.List;

import com.intexsoft.cli.controller.CLIController;

import org.apache.log4j.Logger;

/**
 * Implementation abstract CLIView class for processing inputed from console
 * command and displaying result.
 */
public class CLIConsoleView extends CLIView {

    private static Logger log = Logger.getLogger(CLIConsoleView.class);

    public CLIConsoleView(CLIController controller) {
        super(controller);
    }

    /**
     * Implementation the abstract method for displaying results to console.
     */
    public void display(List<String> messages) {
        for (String message: messages) { 
            System.out.println(message);
        }
    }

    /**
     * Method for running user input loop.
     */
    public void getUserInput() {
        try {
            System.out.println("Enter \"help\" for intructions.");
            BufferedReader br = new
                BufferedReader(new InputStreamReader(System.in));

            for(;;) {
                System.out.print("> ");
                expression = br.readLine();
                
                if (!expression.equals(""))
                    expressionChanged();
                else
                    continue;
            }
        } catch (IOException e) {
            log.error("Error in getUserInput(): " + e.getMessage());
        }
    }
}

