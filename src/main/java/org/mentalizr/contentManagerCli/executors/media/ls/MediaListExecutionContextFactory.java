package org.mentalizr.contentManagerCli.executors.media.ls;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.exceptions.InconsistencyException;
import org.mentalizr.contentManagerCli.executors.ExecutionContext;
import org.mentalizr.contentManagerCli.executors.ExecutionContextFactory;

public class MediaListExecutionContextFactory extends ExecutionContextFactory {

    @Override
    public ExecutionContext createExecutionContext(CliCall cliCall) throws InconsistencyException {
        return new MediaListExecutionContext(cliCall);
    }

}
