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
import org.mentalizr.contentManagerCli.console.Output;
import org.mentalizr.contentManagerCli.console.OutputFormatter;
import org.mentalizr.contentManagerCli.console.OutputFormatterBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExecutor implements CommandExecutor {

    protected static final OutputFormatter outputFormatterOk
            = new OutputFormatterBuilder().withTypeOK().withProgramTag().build();
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

        Output.summaryOut(executionSummary);
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
                Output.out(outputFormatterNormal, "Target programs: all");
            return ProgramDirs.getAllProgramDirs(executionContext.getContentRootPath());
        } else {
            if (executionContext.isVerbose())
                Output.out(outputFormatterNormal, "Target programs: " + Strings.listing(programs, ", "));
            List<Path> paths = ProgramDirs.getProgramDirs(executionContext.getContentRootPath(), programs);
            if (executionContext.isVerbose())
                Output.out(outputFormatterNormal, "Found programs: " + Strings.listing(paths.stream().map(Path::toString).collect(Collectors.toList()), ", "));
            return paths;
        }
    }

    private List<Program> parseProgramRepos(ExecutionSummary executionSummary, List<Path> programPaths) throws CommandExecutorException {
        List<Program> programs = new ArrayList<>();
        for (Path programPath : programPaths) {
            try {
                programs.add(new Program(programPath));
            } catch (ProgramManagerException e) {
                outErrorInconsistentProgram(programPath, e);
                executionSummary.incFailed();
            }
        }
        return programs;
    }

    private void processPrograms(ExecutionContext executionContext, ExecutionSummary executionSummary, List<Program> programs) throws CommandExecutorException {
        for (Program program : programs) {
            try {
                processProgram(program);
                outOk(program);
                executionSummary.incSuccess();
            } catch (ProgramManagerException e) {
                outError(program, e);
                if (executionContext.isStacktrace()) Output.stacktrace(e);
                executionSummary.incFailed();
            }
        }
    }

    protected void outOk(Program program) {
        Output.out(outputFormatterOk, program.getName(), getMessageTextSuccess());
    }

    protected void outError(Program program, Exception e) {
        Output.out(outputFormatterError, program.getName(), getMessageTextFailed() + " Cause: " + e.getMessage());
    }

    protected void outErrorInconsistentProgram(Path programPath, Exception cause) {
        Output.out(outputFormatterError, programPath.getFileName().toString(), getMessageTextFailed() + " No valid program. Cause: " + cause.getMessage());
    }

    protected abstract String getOperationName();

    protected abstract String getMessageTextSuccess();

    protected abstract String getMessageTextFailed();

    protected abstract void processProgram(Program program) throws ProgramManagerException;

}
