package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentRoot.HtmlDir;
import org.mentalizr.contentManagerCli.ContentManagerCli;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.console.Output;
import org.mentalizr.contentManagerCli.console.OutputFormatter;
import org.mentalizr.contentManagerCli.console.OutputFormatterBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CleanExecutor extends AbstractExecutor implements CommandExecutor {

    private static final OutputFormatter outputFormatterOk
            = new OutputFormatterBuilder().withTypeOK().withProgramTag().build();

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        boolean force = cliCall.getOptionParserResultSpecific().hasOption(ContentManagerCli.OPTION__CLEAN__FORCE);

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        cleanPrograms(programPaths, force);
    }

    @Override
    protected void processProgram(Program program) throws ProgramManagerException {
        program.clean();
        System.out.println("[" + program.getName() + "] cleaned.");
    }

    private void cleanPrograms(List<Path> programPaths, boolean force) throws CommandExecutorException {
        for (Path programPath : programPaths) {
            try {
//                Program.assertHasHtmlDir(programPath);
                if (!force) {
                    Program.assertProgramDirByPlausibility(programPath);
                }
                cleanProgram(programPath);
            } catch (ProgramManagerException e) {
                throw new CommandExecutorException(e.getMessage(), e);
            }
        }
    }

    private void cleanProgram(Path programPath) throws CommandExecutorException {
        Path htmlPath = programPath.resolve(HtmlDir.DIR_NAME);
        try {
            if (Files.exists(htmlPath))
                FileUtils.rmDir(htmlPath);
        } catch (IOException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
        Output.out(outputFormatterOk, programPath.getFileName().toString(), "Program repo cleaned successfully.");
    }

}
