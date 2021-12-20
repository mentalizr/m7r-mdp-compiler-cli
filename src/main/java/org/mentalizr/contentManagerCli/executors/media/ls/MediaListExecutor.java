package org.mentalizr.contentManagerCli.executors.media.ls;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentRoot.MdpDir;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.Build;
import org.mentalizr.contentManagerCli.consistency.NoOrphanedMediaResourcesValidator;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.contentManagerCli.executors.AbstractExecutor;
import org.mentalizr.contentManagerCli.executors.ExecutionContext;
import org.mentalizr.contentManagerCli.executors.ExecutionContextFactory;

import java.util.Set;

public class MediaListExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "list media resources";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Media resources listed successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error listing media resources.";
    }

    @Override
    protected ExecutionContextFactory getExecutionContextFactory() {
        return new MediaListExecutionContextFactory();
    }

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {

        MediaListExecutionContext mediaListExecutionContext
                = (MediaListExecutionContext) executionContext;

        try {
            Program program = new Program(programPath.getPath());
            if (!program.hasHtmlDir()) {
                Console.errorProgramOut(program.getName(), "Program not built yet.");
                return false;
            }

            Set<String> referencedMediaResources = Build.getReferencesMediaResources(program);

            if (mediaListExecutionContext.isShowOrphanedOnly()) {
                NoOrphanedMediaResourcesValidator noOrphanedMediaResourcesValidator
                        = NoOrphanedMediaResourcesValidator.create(program, referencedMediaResources);
                Set<String> orphanedMediaResources = noOrphanedMediaResourcesValidator.getOrphanedMediaResources();
                outputResources(orphanedMediaResources, programPath, mediaListExecutionContext);
            } else {
                outputResources(referencedMediaResources, programPath, mediaListExecutionContext);
            }
            return true;
        } catch (ContentManagerException e) {
            Console.errorProgramOut(programPath.getProgramName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }
    }

    private void outputResources(
            Set<String> mediaResources, ProgramPath programPath, MediaListExecutionContext mediaListExecutionContext) {

        if (mediaListExecutionContext.isAbsolute()) {
            mediaResources.forEach(
                    m -> System.out.println(programPath.getPath().resolve(MdpDir.DIR_NAME).resolve(m).toAbsolutePath())
            );
        } else {
            mediaResources.forEach(System.out::println);
        }
    }

}
