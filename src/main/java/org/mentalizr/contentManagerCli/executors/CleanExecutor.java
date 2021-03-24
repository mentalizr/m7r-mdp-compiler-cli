package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.serviceObjects.frontend.program.ProgramSOX;

public class CleanExecutor extends AbstractBuildExecutor implements CommandExecutor {

    @Override
    protected void callProgramMethod(Program program) throws ProgramManagerException {
        program.clean();
    }

}
