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
import java.util.stream.Collectors;

public abstract class AbstractBuildExecutor implements CommandExecutor {

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
            if (executionContext.isVerbose())
                System.out.println("Target programs: all");
            return ProgramDirs.getAllProgramDirs(executionContext.getContentRootPath());
        } else {
            if (executionContext.isVerbose())
                System.out.println("Target programs: " + Strings.listing(programs, ", "));
            List<Path> paths = ProgramDirs.getProgramDirs(executionContext.getContentRootPath(), programs);
            if (executionContext.isVerbose())
                System.out.println("Found programs: " + Strings.listing(paths.stream().map(Path::toString).collect(Collectors.toList()), ", "));
            return paths;
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
                callProgramMethod(program);
            } catch (ProgramManagerException e) {
                throw new CommandExecutorException("[" + program.getName() + "] " +
                        e.getMessage(), e);
            }
        }
    }

    protected abstract void callProgramMethod(Program program) throws ProgramManagerException;

}
