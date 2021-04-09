package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManagerCli.ContentManagerCliException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramDirs;
import org.mentalizr.contentManagerCli.console.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExecutor implements CommandExecutor {

    protected static final OutputFormatter outputFormatterOk
            = new OutputFormatterBuilder().withTypeOK().withProgramTag().build();
    protected static final OutputFormatter outputFormatterOkSimple
            = new OutputFormatterBuilder().withTypeOK().build();
    protected static final OutputFormatter outputFormatterError
            = new OutputFormatterBuilder().withTypeError().withProgramTag().build();
    protected static final OutputFormatter outputFormatterNormal
            = new OutputFormatterBuilder().withTypeNormal().build();

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary(getOperationName());

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        List<Program> programs = parseProgramRepos(executionSummary, programPaths);
        processPrograms(executionContext, executionSummary, programs);

        Console.summaryOut(executionSummary);
        if (executionSummary.isFailed())
            throw new CommandExecutorException();
    }

    protected ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            return new ExecutionContext(cliCall);
        } catch (ContentManagerCliException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

    protected List<Path> obtainProgramPaths(List<String> programs, ExecutionContext executionContext) throws CommandExecutorException {
        if (programs.size() == 0) {
            if (executionContext.isVerbose())
                Console.out("Target programs: all");
            return ProgramDirs.getAllProgramDirs(executionContext.getContentRootPath());
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
            } catch (ProgramManagerException e) {
                Console.errorNoValidProgramOut(programPath.getFileName().toString(), getMessageTextFailed(), e);
//                outErrorInconsistentProgram(programPath, e);
                executionSummary.incFailed();
            }
        }
        return programs;
    }

    private void processPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<Program> programs) throws CommandExecutorException {
        for (Program program : programs) {
            try {
                processProgram(executionContext, program);
                Console.okProgramOut(program.getName(), getMessageTextSuccess());
//                ConsoleOutput.printOk(program, getMessageTextSuccess());
                executionSummary.incSuccess();
            } catch (ProgramManagerException e) {
                Console.errorProgramOut(program.getName(), getMessageTextFailed());
//                ConsoleOutput.printError(program, getMessageTextFailed(), e);
                if (executionContext.isStacktrace()) Console.stacktrace(e);
                executionSummary.incFailed();
            }
        }
    }

//    protected void outOk(Program program) {
//        Console.out(outputFormatterOk, program.getName(), getMessageTextSuccess());
//    }
//
//    protected void outOk(String message) {
//        Console.out(outputFormatterOkSimple, message);
//    }
//
//    protected void outError(Program program, Exception e) {
//        Console.out(outputFormatterError, program.getName(), getMessageTextFailed() + " Cause: " + e.getMessage());
//    }
//
//    protected void outErrorInconsistentProgram(Path programPath, Exception cause) {
//        Console.out(outputFormatterError, programPath.getFileName().toString(), getMessageTextFailed() + " No valid program. Cause: " + cause.getMessage());
//    }
//
//    protected void outError(String message) {
//        Console.out(outputFormatterError, message);
//    }
//
//    protected void out(String message) {
//        Console.out(outputFormatterNormal, message);
//    }

    protected abstract String getOperationName();

    protected abstract String getMessageTextSuccess();

    protected abstract String getMessageTextFailed();

    protected abstract void processProgram(ExecutionContext executionContext, Program program) throws ProgramManagerException;

}
