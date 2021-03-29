package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;

import java.util.List;

public class CheckExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        List<String> parameterList = cliCall.getParameterList();
        if (parameterList.size() == 0) {
            System.out.println(" ...");
        } else {
            System.out.println("Compile ...");
            for (String parameter : parameterList) {
                System.out.println(parameter);
            }

        }

    }

}
