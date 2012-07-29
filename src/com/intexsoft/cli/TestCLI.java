package com.intexsoft.cli;

import com.intexsoft.cli.controller.CLIController;
import com.intexsoft.cli.model.CLIModel;
import com.intexsoft.cli.view.CLIConsoleView;

/**
 * The class for tetsting work of the Central Libraries Index.
 */
class TestCLI {
    
    final static String libDir = "/home/jarek/src/intexsoft/cli-web/libraries";

    public static void main(String args[]) {
        CLIModel model = new CLIModel(libDir);
        CLIController controller = new CLIController(model);

        CLIConsoleView view = new CLIConsoleView(controller);
        view.getUserInput();
    }
}
