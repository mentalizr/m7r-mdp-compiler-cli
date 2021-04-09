package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.build.BuildFail;
import org.mentalizr.contentManager.build.BuildSummary;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.MdpBuildHandler;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.contentManagerCli.console.ConsoleOutput;

public class BuildExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected void processProgram(ExecutionContext executionContext, Program program) throws ProgramManagerException {
        program.clean();
        BuildSummary buildSummary = program.build(new MdpBuildHandler());

        if (executionContext.isVerbose()) verboseOut(buildSummary);
        errorOut(buildSummary);
        buildSummaryOut(program, buildSummary);
    }

    @Override
    protected String getOperationName() {
        return "build";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Program build successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error building program.";
    }

    private void verboseOut(BuildSummary buildSummary) {
        for (MdpFile mdpFileSuccess : buildSummary.getSuccessfulMpdFiles()) {
            Console.okOut(mdpFileSuccess.getId());
        }
    }

    private void errorOut(BuildSummary buildSummary) {
        for (BuildFail buildFail : buildSummary.getFailedMdpFiles()) {
            Console.errorOut(buildFail.getMdpFile().getId() + ": " + buildFail.getException().getMessage());
        }
    }

    private void buildSummaryOut(Program program, BuildSummary buildSummary) {
        String summary = buildSummary.getTotalNrOfMdpFiles()
                + " mdp file" + (buildSummary.getTotalNrOfMdpFiles() > 1 ? "s" : "") + " compiled: "
                + buildSummary.getNrOfSuccessfulMdpFiles() + " ok, "
                + buildSummary.getNrOfFailedMdpFiles() + " failed.";
        Console.programOut(program.getName(), summary);
    }

}
