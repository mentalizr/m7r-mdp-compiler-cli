package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.serviceObjects.frontend.program.ProgramSOX;

public class ShowExecutor extends AbstractBuildExecutor implements CommandExecutor {

    @Override
    protected void callProgramMethod(Program program) throws ProgramManagerException {
        org.mentalizr.serviceObjects.frontend.program.Program programSO = program.asProgram();
        System.out.println(ProgramSOX.toJsonWithFormatting(programSO));
    }

}
