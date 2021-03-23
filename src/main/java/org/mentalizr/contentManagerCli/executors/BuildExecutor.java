package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManagerCli.ContentManagerCliException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramDirs;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BuildExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        ExecutionContext executionContext = initExecutionContext(cliCall);
        List<Path> programPaths = new ArrayList<>();

        List<String> programs = cliCall.getParameterList();
        if (programs.size() == 0) {
            System.out.println("Build of all programs ...");
        } else {
            System.out.println("Build of ...");
            for (String parameter : programs) {
                try {
                    programPaths.add(ProgramDirs.getProgramDir(executionContext.getContentRootPath(), parameter));
                } catch (ContentManagerCliException e) {
                    throw new CommandExecutorException(e.getMessage(), e);
                }
                System.out.println(parameter);
            }

        }

    }

    private ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            return new ExecutionContext(cliCall);
        } catch (ContentManagerCliException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

}
