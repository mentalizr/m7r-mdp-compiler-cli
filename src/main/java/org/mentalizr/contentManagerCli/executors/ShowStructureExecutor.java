package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.serviceObjects.frontend.program.ProgramSO;
import org.mentalizr.serviceObjects.frontend.program.ProgramSOX;

public class ShowStructureExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "show structure";
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
    protected boolean processProgram(ExecutionContext executionContext, Program program) {
        if (program.isBuilt()) {
            ProgramSO programSO = program.asProgram();
            System.out.println(ProgramSOX.toJsonWithFormatting(programSO));
            return true;
        } else {
            Console.errorProgramOut(program.getName(), "Program not built yet.");
            return false;
        }
    }

}
