package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.Build;
import org.mentalizr.contentManagerCli.build.target.ReferencedMediaResources;
import org.mentalizr.contentManagerCli.console.Console;

import java.util.Set;

public class ShowMediaResourcesExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "show media resources";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Media resources shown successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error showing media resources.";
    }

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {
        try {
            Program program = new Program(programPath.getPath());
            if (program.hasHtmlDir()) {
                Set<String> referencedMediaResources = Build.getReferencesMediaResources(program);
                referencedMediaResources.forEach(System.out::println);
                return true;
            } else {
                Console.errorProgramOut(program.getName(), "Program not built yet.");
                return false;
            }
        } catch (ContentManagerException e) {
            Console.errorProgramOut(programPath.getProgramName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }
    }

}
