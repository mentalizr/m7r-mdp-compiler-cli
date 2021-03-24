package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManagerCli.ContentManagerCliException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.MdpBuildHandler;
import org.mentalizr.contentManagerCli.ProgramDirs;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BuildExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        List<Program> programs = parseProgramRepos(programPaths);
        buildPrograms(programs);
    }

    private ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            return new ExecutionContext(cliCall);
        } catch (ContentManagerCliException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

    private List<Path> obtainProgramPaths(List<String> programs, ExecutionContext executionContext) throws CommandExecutorException {
        if (programs.size() == 0) {
            System.out.println("Build of all programs ...");
            return ProgramDirs.getAllProgramDirs(executionContext.getContentRootPath());
        } else {
            System.out.println("Building the following programs: " + Strings.listing(programs, ", "));
            return ProgramDirs.getProgramDirs(executionContext.getContentRootPath(), programs);
        }
    }

    private List<Program> parseProgramRepos(List<Path> programPaths) throws CommandExecutorException {
        List<Program> programs = new ArrayList<>();
        for (Path programPath : programPaths) {
            try {
                programs.add(new Program(programPath));
            } catch (ProgramManagerException e) {
                throw new CommandExecutorException("Program repository [" + programPath.getFileName()
                        + "] is inconsistent. " + e.getMessage(), e);
            }
        }
        return programs;
    }

    private void buildPrograms(List<Program> programs) throws CommandExecutorException {
        for (Program program : programs) {
            try {
                program.build(new MdpBuildHandler());
            } catch (ProgramManagerException e) {
                throw new CommandExecutorException("Error on building program [" + program.getName() + "]. " +
                        e.getMessage(), e);
            }
        }
    }

}
