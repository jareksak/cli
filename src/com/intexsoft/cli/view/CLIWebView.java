package com.intexsoft.cli.view;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intexsoft.cli.controller.CLIController; 

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Implementation of the abstract CLIView class for processing inputed from bowser 
 * command and displaying result.
 */
public class CLIWebView extends CLIView {
    
    private static Logger log = Logger.getLogger(CLIWebView.class);

    /** Reference to a object for forwarding a result message. */
    private RequestDispatcher dispatcher; 

    private HttpServletRequest request;
    
    private HttpServletResponse response;

    public CLIWebView(CLIController controller) {
        super(controller);
    }
   
    /**
     * A method for getting expression from a web-form and sending to a
     * controller.
     */
    public void sendExpression(String expression, RequestDispatcher dispatcher,
                HttpServletRequest request, HttpServletResponse response) {
        this.expression = expression;
        this.dispatcher = dispatcher;
        this.request = request;
        this.response = response;
        expressionChanged();
    }

    /**
     * Implementation the abstract method for displaying results in a web-page.
     */
    public void display(List<String> messages) {
        String resultMessage = "";
        for (String message: messages) { 
            resultMessage += message + "\n";
        }
        resultMessage = StringEscapeUtils.escapeHtml4(resultMessage);
        
        request.setAttribute("expression", expression);
        request.setAttribute("message", resultMessage);

        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

