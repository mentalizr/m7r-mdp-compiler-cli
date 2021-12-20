package org.mentalizr.contentManagerCli.executors.media.prune;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentRoot.MdpDir;
import org.mentalizr.contentManager.helper.Nio2Helper;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.Build;
import org.mentalizr.contentManagerCli.consistency.NoOrphanedMediaResourcesValidator;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.contentManagerCli.executors.AbstractExecutor;
import org.mentalizr.contentManagerCli.executors.DefaultExecutionContextFactory;
import org.mentalizr.contentManagerCli.executors.ExecutionContext;
import org.mentalizr.contentManagerCli.executors.ExecutionContextFactory;
import org.mentalizr.contentManagerCli.executors.media.ls.MediaListExecutionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

public class MediaPruneExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "prune media resources";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "media resources pruned successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error pruning media resources.";
    }

    @Override
    protected ExecutionContextFactory getExecutionContextFactory() {
        return new DefaultExecutionContextFactory();
    }

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {

        try {
            Program program = new Program(programPath.getPath());
            if (!program.hasHtmlDir()) {
                Console.errorProgramOut(program.getName(), "Program not built yet.");
                return false;
            }

            Set<String> referencedMediaResources = Build.getReferencesMediaResources(program);

            NoOrphanedMediaResourcesValidator noOrphanedMediaResourcesValidator
                    = NoOrphanedMediaResourcesValidator.create(program, referencedMediaResources);
            Set<String> orphanedMediaResources = noOrphanedMediaResourcesValidator.getOrphanedMediaResources();

            if (!noOrphanedMediaResourcesValidator.hasOrphanedMediaResources()) {
                Console.okProgramOut(programPath.getProgramName(), "No unreferenced media resources found to prune.");
                return true;
            }

            Path mediaPrunedDir = obtainMediaPrunedDir(programPath);
            moveOrphanedMedia(orphanedMediaResources, programPath, mediaPrunedDir);

            Console.okProgramOut(programPath.getProgramName(), orphanedMediaResources.size()
                    + " unreferenced media resources pruned successfully.");
            return true;
        } catch (ContentManagerException | IOException e) {
            Console.errorProgramOut(programPath.getProgramName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }
    }

    private Path obtainMediaPrunedDir(ProgramPath programPath) throws IOException {
        Path mediaPrunedDir = programPath.getPath().resolve("media-pruned");
        if (!Nio2Helper.isExistingDir(mediaPrunedDir)) {
            Files.createDirectory(mediaPrunedDir);
        }
        return mediaPrunedDir;
    }

    private void moveOrphanedMedia(Set<String> orphanedMedia, ProgramPath programPath, Path mediaPrunedDir) throws IOException {
        for (String media : orphanedMedia) {
            Path source = programPath.getPath().resolve(MdpDir.DIR_NAME).resolve(media);
            Path destination = mediaPrunedDir.resolve(media);
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
