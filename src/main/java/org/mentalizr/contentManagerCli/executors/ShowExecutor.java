package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.serviceObjects.frontend.program.ProgramSOX;

public class ShowExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "show";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Program structure shown successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error showing program structure.";
    }

    @Override
    protected void processProgram(ExecutionContext executionContext, Program program) throws ContentManagerException {
        if (program.isBuilt()) {
            org.mentalizr.serviceObjects.frontend.program.Program programSO = program.asProgram();
            System.out.println(ProgramSOX.toJsonWithFormatting(programSO));
        } else {
            throw new ContentManagerException("Program not built yet.");
        }
    }

}
