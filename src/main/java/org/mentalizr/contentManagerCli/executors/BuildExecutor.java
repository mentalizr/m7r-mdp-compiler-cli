package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ProgramManagerException;
import org.mentalizr.contentManagerCli.MdpBuildHandler;

public class BuildExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected void processProgram(Program program) throws ProgramManagerException {
        program.clean();
        program.build(new MdpBuildHandler());
    }

    @Override
    protected String getOperationName() {
        return "build";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "";
    }

    @Override
    protected String getMessageTextFailed() {
        return null;
    }



}
