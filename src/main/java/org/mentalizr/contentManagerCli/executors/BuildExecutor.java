package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManager.validator.ValidationError;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.Build;
import org.mentalizr.contentManagerCli.build.BuildSummary;
import org.mentalizr.contentManagerCli.build.FailedMdpFile;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.util.Set;

public class BuildExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {
        BuildSummary buildSummary;
        try {
            buildSummary = Build.cleanBuild(programPath);
        } catch (ContentManagerException e) {
            Console.errorProgramOut(programPath.getProgramName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }

        output(programPath, executionContext, buildSummary);

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

    private void output(ProgramPath programPath, ExecutionContext executionContext, BuildSummary buildSummary) {
        if (executionContext.isVerbose()) verboseOut(buildSummary);
        buildOut(programPath, buildSummary);
    }

    private void verboseOut(BuildSummary buildSummary) {
        for (MdpFile mdpFileSuccess : buildSummary.getSuccessfulMpdFiles()) {
            Console.okOut(mdpFileSuccess.getId());
        }
    }

    private void buildOut(ProgramPath programPath, BuildSummary buildSummary) {
        errorOut(programPath, buildSummary);
        summaryOut(programPath, buildSummary);
    }

    private void errorOut(ProgramPath programPath, BuildSummary buildSummary) {
        for (ValidationError validationError : buildSummary.getValidationErrors()) {
            Console.errorOut(validationError.getMessage());
        }

        for (FailedMdpFile failedMdpFile : buildSummary.getFailedMdpFiles()) {

            String id = failedMdpFile.getMdpFile().getId();
            Exception exception = failedMdpFile.getException();

            if (exception instanceof MDPSyntaxError) {
                MDPSyntaxError mdpSyntaxError = (MDPSyntaxError) failedMdpFile.getException();
                Console.errorOut(MDPSyntaxError.getExtendedMessage(id, mdpSyntaxError));
            } else {
                Console.errorOut(failedMdpFile.getMdpFile().getId() + ": " + failedMdpFile.getException().getMessage());
            }
        }

        if (buildSummary.hasOrphanedMediaResources()) {
            Set<String> orphanedMediaResources = buildSummary.getOrphanedMediaResources();
            String orphansAsString =
                    Strings.listing(orphanedMediaResources, ", ", "", "", "[", "]");
            Console.warnOut("[" + programPath.getProgramName() + "] Unreferenced media resources: " + orphansAsString);
        }
    }

    private void summaryOut(ProgramPath programPath, BuildSummary buildSummary) {
        String summary = buildSummaryString(buildSummary);
        if (buildSummary.isSuccess()) {
            Console.okProgramOut(programPath.getProgramName(), summary + " " + getMessageTextSuccess());
        } else {
            Console.errorProgramOut(programPath.getProgramName(), summary + " " + getMessageTextFailed());
        }
    }

    private String buildSummaryString(BuildSummary buildSummary) {
        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder
                .append(buildSummary.getTotalNrOfMdpFiles())
                .append(" mdp file")
                .append(buildSummary.getTotalNrOfMdpFiles() > 1 ? "s" : "")
                .append(" compiled: ").append(buildSummary.getNrOfSuccessfulMdpFiles())
                .append(" ok, ")
                .append(buildSummary.getNrOfFailedMdpFiles())
                .append(" failed.");
        if (buildSummary.hasValidationErrors()) {
            summaryBuilder
                    .append(" ")
                    .append(buildSummary.getNrOfValidationErrors())
                    .append(" program validation error")
                    .append(buildSummary.getNrOfValidationErrors() > 1 ? "s" : "")
                    .append(".");
        }
        if (buildSummary.hasOrphanedMediaResources()) {
            summaryBuilder
                    .append(" ")
                    .append(buildSummary.getNrOfOrphanedMediaResources())
                    .append(" unreferenced media resource")
                    .append(buildSummary.getNrOfOrphanedMediaResources() > 1 ? "s" : "")
                    .append(".");
        }
        return summaryBuilder.toString();
    }

}
