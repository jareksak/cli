package com.intexsoft.cli.command;

import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.intexsoft.cli.model.CLIModel;

import org.apache.log4j.Logger;

/**
 * The factory for creating objects of commands.
 */
public class CLICommandFactory {

    private static Logger log = Logger.getLogger(CLICommandFactory.class);

    private final Map<String, Class> commands = createMap(); 

    private Map<String, Class> createMap() {
        Map<String, Class> result = new HashMap<String, Class>();
        result.put("find", FindCommand.class);
        result.put("order", OrderCommand.class);
        result.put("return", ReturnCommand.class);
        result.put("help", HelpCommand.class);
        
        return result;
    }

    /**
     * The factory method.
     *
     * @param expression Text of inputed command.
     * @param model      Model for executing the command.
     * @return           Object of the command. Return null if the command is 
     *                   unknown or syntax error.
     */
    public Command getCommand(String expression, CLIModel model) {
        
        String commandName = getCommandName(expression);
        Map<String, String> parameters = getParameters(expression);

        Command result = null;
        
        Class cmdClass = commands.get(commandName);
        if (cmdClass != null) {
            try {
                Command cmd = (Command) cmdClass.newInstance();
                cmd.setCommandName(commandName);
                cmd.setParameters(parameters);
                cmd.setModel(model);

                if (cmd.isValid()) {
                    result = cmd;
                }
            } catch (InstantiationException e) {
                log.error("Error in getCommand(). commandName: " +
                           commandName + ". " + e.getMessage());
            } catch (IllegalAccessException e) {
                log.error("Error in getCommand(). commandName: " +
                           commandName + ". " + e.getMessage());
            } 
        }

        return result;
    }
    
    /**
     * @param expression Text of inputed command.
     * @return           The name of inputed command, or the enpty string.
     */
    private String getCommandName(String expression) {

        String result = "";
        StringTokenizer sTokenizer = new StringTokenizer(expression);

        if (sTokenizer.hasMoreTokens()) {
            result = sTokenizer.nextToken().toLowerCase();
        }

        if (log.isDebugEnabled()) {
            log.debug("Got commandName: " + result);
        }

        return result;
    }
    
    /**
     * @param expression Text of inputed command.
     * @return           The map object with command parameters.
     */
    private Map<String, String> getParameters(String expression) {

        Map<String, String> result = new HashMap<String, String>();
        
        StringTokenizer sTokenizer = new StringTokenizer(expression);
        if (sTokenizer.hasMoreTokens()) {
            String tmp = sTokenizer.nextToken().toLowerCase();

            while (sTokenizer.hasMoreTokens()) {
                String pToken = sTokenizer.nextToken();
                StringTokenizer pTokenizer = new StringTokenizer(pToken, "=");
                
                String parameter = pTokenizer.nextToken().toLowerCase();
                    
                if (pTokenizer.hasMoreTokens()) {
                    String value = pTokenizer.nextToken();
                    result.put(parameter, value);
                } else {
                    result.put(parameter, null);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Got parameters: " + result);
        }

        return result;
    }
}

