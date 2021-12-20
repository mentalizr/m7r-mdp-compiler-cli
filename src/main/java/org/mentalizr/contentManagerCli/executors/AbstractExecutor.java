package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.exceptions.InconsistencyException;
import org.mentalizr.contentManagerCli.ProgramDirs;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.console.Console;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        ExecutionContext executionContext = initExecutionContext(cliCall);
        ExecutionSummary executionSummary = new ExecutionSummary(getOperationName());
        List<String> specifiedPrograms = cliCall.getParameterList();
        List<ProgramPath> programPaths = obtainProgramPaths(specifiedPrograms, executionContext);

        processPrograms(executionContext, executionSummary, programPaths);

        if (!executionContext.isOmitSummary())
            Console.summaryOut(executionSummary);

        // TODO ???
        if (executionSummary.isFailed())
            throw new CommandExecutorException();
    }

    protected ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            ExecutionContextFactory executionContextFactory = getExecutionContextFactory();
            return executionContextFactory.createExecutionContext(cliCall);
        } catch (InconsistencyException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

    protected List<ProgramPath> obtainProgramPaths(List<String> specifiedPrograms, ExecutionContext executionContext) throws CommandExecutorException {
        if (specifiedPrograms.size() == 0) {
            if (executionContext.isVerbose())
                Console.out("Target programs: all");
            List<ProgramPath> programPaths = ProgramDirs.getAllProgramPaths(executionContext.getContentRootPath());
            programPaths.sort(Comparator.comparing(ProgramPath::getProgramName));
            return programPaths;
        } else {
            if (executionContext.isVerbose())
                Console.out("Target programs: " + Strings.listing(specifiedPrograms, ", "));
            List<ProgramPath> programPaths = ProgramDirs.getProgramDirs(executionContext.getContentRootPath(), specifiedPrograms);
            if (executionContext.isVerbose())
                Console.out("Found programs: " + Strings.listing(programPaths.stream().map(ProgramPath::toString).collect(Collectors.toList()), ", "));
            return programPaths;
        }
    }

    private void processPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<ProgramPath> programPaths) {
        for (ProgramPath programPath : programPaths) {
            boolean success = processProgram(executionContext, programPath);

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

    protected abstract ExecutionContextFactory getExecutionContextFactory();

    protected abstract boolean processProgram(ExecutionContext executionContext, ProgramPath programPath);

}
