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
    private static final OutputFormatter outputFormatterError
            = new OutputFormatterBuilder().withTypeError().withProgramTag().build();

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
//        boolean force = cliCall.getOptionParserResultSpecific().hasOption(ContentManagerCli.OPTION__CLEAN__FORCE);

        List<Path> programPaths = obtainProgramPaths(cliCall.getParameterList(), executionContext);
        cleanPrograms(executionContext, programPaths);
    }

    @Override
    protected void processProgram(Program program) throws ProgramManagerException {
        throw new RuntimeException("Intentionally not implemented.");
//        program.clean();
//        Output.out(outputFormatterOk, program.getName(), "Program repo cleaned successfully.");
    }

    private void cleanPrograms(ExecutionContext executionContext, List<Path> programPaths) {
        for (Path programPath : programPaths) {
//            try {
////                Program.assertHasHtmlDir(programPath);
//                if (!force) {
//                    Program.assertProgramDirByPlausibility(programPath);
//                }
                cleanProgram(executionContext, programPath);
//            } catch (ProgramManagerException e) {
//                throw new CommandExecutorException(e.getMessage(), e);
//            }
        }
    }

    private void cleanProgram(ExecutionContext executionContext, Path programPath) {

        try {
            Program program = new Program(programPath);
            program.clean();
            Output.out(outputFormatterOk, program.getName(), "Program repo cleaned successfully.");
        } catch (ProgramManagerException e) {
            Output.out(outputFormatterError, programPath.getFileName().toString(), e.getMessage());
            if (executionContext.isStacktrace()) {
                e.printStackTrace();
            }
        }


//        Path htmlPath = programPath.resolve(HtmlDir.DIR_NAME);
//        try {
//            Program program = new Program(programPath);
//            if (Files.exists(htmlPath))
//                FileUtils.rmDir(htmlPath);
//        } catch (IOException e) {
//            throw new CommandExecutorException(e.getMessage(), e);
//        }
//        Output.out(outputFormatterOk, programPath.getFileName().toString(), "Program repo cleaned successfully.");
    }

//    private void forceCleanProgram(ExecutionContext executionContext, Path programPath) {
//        Path htmlPath = programPath.resolve(HtmlDir.DIR_NAME);
//        try {
//            if (Files.exists(htmlPath))
//                FileUtils.rmDir(htmlPath);
//            Output.out(outputFormatterOk, programPath.getFileName().toString(), "Program repo force cleaned successfully.");
//        } catch (IOException e) {
//            Output.out(outputFormatterError, programPath.getFileName().toString(), "Could not clean program repo. Caused by " + e.getClass().getSimpleName() + ": " + e.getMessage());
//        }
//    }

}
