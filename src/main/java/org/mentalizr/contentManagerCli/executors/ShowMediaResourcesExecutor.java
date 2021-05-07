package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.buildHandler.MdpBuildHandlerFactory;
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
    protected boolean processProgram(ExecutionContext executionContext, Program program) {
        if (program.isBuilt()) {
            try {
                Set<String> referencedMediaResources = program.getReferencedMediaResources(new MdpBuildHandlerFactory());
                referencedMediaResources.forEach(System.out::println);
                return true;
            } catch (ContentManagerException e) {
                Console.errorProgramOut(program.getName(), e.getMessage());
                if (executionContext.isStacktrace()) e.printStackTrace();
                return false;
            }
        } else {
            Console.errorProgramOut(program.getName(), "Program not built yet.");
            return false;
        }
    }

}
