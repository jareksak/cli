package com.intexsoft.cli;

import com.intexsoft.cli.controller.CLIController;
import com.intexsoft.cli.model.CLIModel;
import com.intexsoft.cli.view.CLIConsoleView;

/**
 * The class for tetsting work of Central Library Index.
 */
class TestCLI {

    public static void main(String args[]) {
        CLIModel model = new CLIModel("libraries");
        CLIController controller = new CLIController(model);

        CLIConsoleView view = new CLIConsoleView(controller);
        view.getUserInput();
    }
}
