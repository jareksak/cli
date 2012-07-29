package com.intexsoft.cli.view;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.intexsoft.cli.model.Book;
import com.intexsoft.cli.controller.CLIController;

import org.apache.log4j.Logger;

/**
 * The abstract base class for view classes.<br>
 *
 * When an expression was changed this class notify the CLIController<br>
 * class over method expressionChanged().<br>
 * The CLIController class send a result to this class over the method update().
 */
public abstract class CLIView {

    private static Logger log = Logger.getLogger(CLIView.class);

    /** Text of the inputed command.*/
    protected String expression = "";

    /** The list of result messages. */
    private List<String> messages;

    private CLIController controller;

    /**
     * @return Text of the inputed command.
     */
    public String getExpression() { return expression; }
 
    public CLIView(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Notify the CLIController about user input.
     */
    protected void expressionChanged() {
        controller.update(this);
    }

    /**
     * Method for processing result from the CLIController.
     */
    public void update(Object object) { 
        messages = new ArrayList<String>();
        if (object instanceof List) {
            List items = (List) object;

            if (log.isDebugEnabled()) {
                log.debug("Getting List: " + items);
            }
            
            if (items.isEmpty()) {
                messages.add("NOTFOUND");
            } else {
                for (Object item: items) {
                    if (item instanceof Map) {
                        Map result = (Map) item;
                        List foundList = (List) result.get("FOUND");
                        if (!foundList.isEmpty()) {
                            for (Object found: foundList) {
                                Book book = (Book) found;
                                messages.add(
                                    "FOUND id=" + book.getId() +
                                    " lib=" + result.get("LIBNAME")
                                );
                            }
                        }

                        foundList = (List) result.get("FOUNDMISSING");
                        if (!foundList.isEmpty()) {
                            for (Object found: foundList) {
                                Book book = (Book) found;
                                messages.add(
                                    "FOUNDMISSING id=" + book.getId() +
                                    " lib=" + result.get("LIBNAME") +
                                    " issued=" + book.getIssued());
                            }
                        }
                    }
                }
            }

        } else if (object instanceof Map) {
            Map result = (Map) object;
            
            if (log.isDebugEnabled()) {
                log.debug("Getting Map: " + result);
            }

            if (result.isEmpty()) {
                messages.add("NOTFOUND");
            } else {
                String command = (String) result.get("COMMAND");

                if (command.equals("order")) {
                    if (result.containsKey("OK")) {
                        Book book = (Book) result.get("OK");
                        messages.add("OK abonent=" + book.getAbonent() +
                                     " date=" + book.getIssued());
                    } else if (result.containsKey("RESERVED")) {
                        Book book = (Book) result.get("RESERVED");
                        messages.add("RESERVED abonent=" + book.getAbonent() +
                                     " date=" + book.getIssued());
                    }
                } else if (command.equals("return")) {
                    if (result.containsKey("OK")) {
                        Book book = (Book) result.get("OK");
                        messages.add("OK abonent=" + book.getAbonent()); 
                    } else if (result.containsKey("ALREADYRETURNED")) {
                        messages.add("ALREADYRETURNED");
                    }
                }
            }

        } else if (object instanceof String) {
            if (log.isDebugEnabled()) {
                log.debug("Getting String: " + (String) object);
            }

            messages.add((String) object);
        }

        display(messages);
    }

    /**
     * Abstract method for displaying results.
     */
    public abstract void display(List<String> messages);
}

