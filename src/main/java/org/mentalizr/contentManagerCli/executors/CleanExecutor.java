package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.console.Console;

import java.nio.file.Path;
import java.util.List;

public class CleanExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary(getOperationName());

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        cleanPrograms(executionContext, executionSummary, programPaths);

        Console.summaryOut(executionSummary);
        if (executionSummary.isFailed())
            throw new CommandExecutorException();
    }

    @Override
    protected String getOperationName() {
        return "clean";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Program repo cleaned successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Cleaning failed.";
    }

    @Override
    protected void processProgram(ExecutionContext executionContext, Program program) {
        throw new RuntimeException("Intentionally not implemented.");
    }

    private void cleanPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<Path> programPaths) {
        for (Path programPath : programPaths) {
                cleanProgram(executionContext, executionSummary, programPath);
        }
    }

    private void cleanProgram(ExecutionContext executionContext, ExecutionSummary executionSummary, Path programPath) {
        try {
            Program program = new Program(programPath);
            program.clean();
            Console.out(outputFormatterOk, program.getName(), getMessageTextSuccess());
            executionSummary.incSuccess();
        } catch (ProgramManagerException e) {
            Console.out(outputFormatterError, programPath.getFileName().toString(), getMessageTextFailed() + " Cause: " + e.getMessage());
            if (executionContext.isStacktrace()) Console.stacktrace(e);
            executionSummary.incFailed();
        }
    }

}
