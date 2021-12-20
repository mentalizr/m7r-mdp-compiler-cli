package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.exceptions.InconsistencyException;

public class DefaultExecutionContextFactory extends ExecutionContextFactory {

    @Override
    public ExecutionContext createExecutionContext(CliCall cliCall) throws InconsistencyException {
        return new ExecutionContext(cliCall);
    }

}
