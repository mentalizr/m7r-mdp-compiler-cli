package org.mentalizr.mdpCompilerCli;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;

import java.util.List;

public class CleanBuildExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        List<String> parameterList = cliCall.getParameterList();
        if (parameterList.size() == 0) {
            System.out.println("Clean build of all programs ...");
        } else {
            System.out.println("Clean build of ...");
            for (String parameter : parameterList) {
                System.out.println(parameter);
            }

        }

    }


}
