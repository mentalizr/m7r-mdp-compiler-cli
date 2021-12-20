package org.mentalizr.contentManagerCli.executors.clean;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManager.Programs;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.contentManagerCli.executors.*;

import java.util.List;

public class CleanExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary(getOperationName());

        List<ProgramPath> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
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
        return "Program repo clean successful.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Clean failed.";
    }

    @Override
    protected ExecutionContextFactory getExecutionContextFactory() {
        return new DefaultExecutionContextFactory();
    }

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {
        throw new RuntimeException("Intentionally not implemented.");
    }

    private void cleanPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<ProgramPath> programPaths) {
        for (ProgramPath programPath : programPaths) {
                cleanProgram(executionContext, executionSummary, programPath);
        }
    }

    private void cleanProgram(ExecutionContext executionContext, ExecutionSummary executionSummary, ProgramPath programPath) {
        try {
            Programs.assertIsProgramDirByPlausibility(programPath.getPath());
            Programs.forceClean(programPath.getPath());

            Console.okProgramOut(programPath.getProgramName(), getMessageTextSuccess());
            executionSummary.incSuccess();
        } catch (ContentManagerException e) {
            Console.errorProgramOut(programPath.getProgramName(), getMessageTextFailed() + " Cause: " + e.getMessage());
            if (executionContext.isStacktrace()) Console.stacktrace(e);
            executionSummary.incFailed();
        }
    }

}
