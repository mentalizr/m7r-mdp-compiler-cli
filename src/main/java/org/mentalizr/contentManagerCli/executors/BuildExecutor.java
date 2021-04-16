package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.build.BuildFail;
import org.mentalizr.contentManager.build.BuildSummary;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.MdpBuildHandler;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

public class BuildExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected boolean processProgram(ExecutionContext executionContext, Program program) {

        BuildSummary buildSummary;
        try {
            program.clean();
            buildSummary = program.build(new MdpBuildHandler());
        } catch (ContentManagerException e) {
            Console.errorProgramOut(program.getName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }

        if (executionContext.isVerbose()) verboseOut(buildSummary);
        buildOut(program, buildSummary);

        return buildSummary.isSuccess();
    }

    @Override
    protected String getOperationName() {
        return "build";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Program build successful.";
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

    private void buildOut(Program program, BuildSummary buildSummary) {
        errorOut(buildSummary);
        summaryOut(program, buildSummary);
    }

    private void errorOut(BuildSummary buildSummary) {
        for (BuildFail buildFail : buildSummary.getFailedMdpFiles()) {

            String id = buildFail.getMdpFile().getId();
            Exception exception = buildFail.getException();

            if (exception instanceof MDPSyntaxError) {
                MDPSyntaxError mdpSyntaxError = (MDPSyntaxError) buildFail.getException();
                Console.errorOut(MDPSyntaxError.getExtendedMessage(id, mdpSyntaxError));
            } else {
                Console.errorOut(buildFail.getMdpFile().getId() + ": " + buildFail.getException().getMessage());
            }
        }
    }

    private void summaryOut(Program program, BuildSummary buildSummary) {
        String summary = buildSummary.getTotalNrOfMdpFiles()
                + " mdp file" + (buildSummary.getTotalNrOfMdpFiles() > 1 ? "s" : "") + " compiled: "
                + buildSummary.getNrOfSuccessfulMdpFiles() + " ok, "
                + buildSummary.getNrOfFailedMdpFiles() + " failed.";
        if (buildSummary.isSuccess()) {
            Console.okProgramOut(program.getName(), summary + " " + getMessageTextSuccess());
        } else {
            Console.errorProgramOut(program.getName(), summary + " " + getMessageTextFailed());
        }
    }

}
