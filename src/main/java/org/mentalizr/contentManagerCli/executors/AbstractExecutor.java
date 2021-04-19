package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ConsistencyException;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramDirs;
import org.mentalizr.contentManagerCli.console.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

//        System.out.println("execute ...");

        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary(getOperationName());

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);

//        System.out.println("found program paths:");
//        for (Path path : programPaths) System.out.println("    " + path);

        List<Program> programs = parseProgramRepos(executionSummary, programPaths);

//        System.out.println("found programs:");
//        for (Program program : programs) System.out.println("    " + program.getName());

        processPrograms(executionContext, executionSummary, programs);

        Console.summaryOut(executionSummary);
        if (executionSummary.isFailed())
            throw new CommandExecutorException();
    }

    protected ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            return new ExecutionContext(cliCall);
        } catch (ConsistencyException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

    protected List<Path> obtainProgramPaths(List<String> programs, ExecutionContext executionContext) throws CommandExecutorException {
        if (programs.size() == 0) {
            if (executionContext.isVerbose())
                Console.out("Target programs: all");
            List<Path> paths = ProgramDirs.getAllProgramDirs(executionContext.getContentRootPath());
            paths.sort(Comparator.comparing(Path::getFileName));
            return paths;
        } else {
            if (executionContext.isVerbose())
                Console.out("Target programs: " + Strings.listing(programs, ", "));
            List<Path> paths = ProgramDirs.getProgramDirs(executionContext.getContentRootPath(), programs);
            if (executionContext.isVerbose())
                Console.out("Found programs: " + Strings.listing(paths.stream().map(Path::toString).collect(Collectors.toList()), ", "));
            return paths;
        }
    }

    private List<Program> parseProgramRepos(ExecutionSummary executionSummary, List<Path> programPaths) throws CommandExecutorException {
        List<Program> programs = new ArrayList<>();
        for (Path programPath : programPaths) {
            try {
                programs.add(new Program(programPath));
            } catch (ContentManagerException e) {
                Console.errorNoValidProgramOut(programPath.getFileName().toString(), getMessageTextFailed(), e);
                executionSummary.incFailed();
            }
        }
        return programs;
    }

    private void processPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<Program> programs) throws CommandExecutorException {
        for (Program program : programs) {
            boolean success = processProgram(executionContext, program);

            if (success) {
                executionSummary.incSuccess();
            } else {
                executionSummary.incFailed();
            }
        }
    }

    protected abstract String getOperationName();

    protected abstract String getMessageTextSuccess();

    protected abstract String getMessageTextFailed();

    protected abstract boolean processProgram(ExecutionContext executionContext, Program program);

}
