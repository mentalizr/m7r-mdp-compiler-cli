package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.console.Output;
import org.mentalizr.contentManagerCli.console.OutputFormatter;
import org.mentalizr.contentManagerCli.console.OutputFormatterBuilder;

import java.nio.file.Path;
import java.util.List;

public class CleanExecutor extends AbstractExecutor implements CommandExecutor {

    private static final OutputFormatter outputFormatterOk
            = new OutputFormatterBuilder().withTypeOK().withProgramTag().build();
    private static final OutputFormatter outputFormatterError
            = new OutputFormatterBuilder().withTypeError().withProgramTag().build();

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary("clean");

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        cleanPrograms(executionContext, executionSummary, programPaths);

        Output.summaryOut(executionSummary);
        if (executionSummary.isFailed())
            throw new CommandExecutorException();
    }

    @Override
    protected void processProgram(Program program) {
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
            Output.out(outputFormatterOk, program.getName(),
                    "Program repo cleaned successfully.");
            executionSummary.incSuccess();
        } catch (ProgramManagerException e) {
            Output.out(outputFormatterError, programPath.getFileName().toString(),
                    "Cleaning failed. Cause: " + e.getMessage());
            if (executionContext.isStacktrace()) Output.stacktrace(e);
            executionSummary.incFailed();
        }
    }

}
