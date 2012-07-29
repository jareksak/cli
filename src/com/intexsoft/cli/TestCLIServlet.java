package com.intexsoft.cli;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intexsoft.cli.model.CLIModel;
import com.intexsoft.cli.view.CLIWebView;
import com.intexsoft.cli.controller.CLIController;

import org.apache.log4j.Logger;

/**
 * The class for working with the Central Library Index
 * over WEB interface
 */
public class TestCLIServlet extends HttpServlet {
    
    private static Logger log = Logger.getLogger(TestCLIServlet.class);

    private CLIModel model;
    private CLIController controller;

    public void init(ServletConfig config) throws ServletException {
        String libDirectory = config.getInitParameter("libDirectory");

        model = new CLIModel(libDirectory);
        if (log.isDebugEnabled()) {
            log.debug("Created new CLIModel object.");
        }

        controller = new CLIController(model);
        if (log.isDebugEnabled()) {
            log.debug("Created new CLIController object.");
        }

    }

    public void service(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
    
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("/view.jsp");

        String expression = request.getParameter("expression"); 

        if (expression == null) {
            request.setAttribute("expression",
                "Here will displayed a user input." +
                "Enter \"help\" for instructions.");
            request.setAttribute("message", "Here will displayed a result.");
            dispatcher.forward(request, response);
        } else {
            CLIWebView view = new CLIWebView(controller);
            if (log.isDebugEnabled()) {
                log.debug("Created new CLIWebView object.");
            }

            view.sendExpression(expression, dispatcher, request, response);
        }
    }
}

