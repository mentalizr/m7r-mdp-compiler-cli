package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.exceptions.InconsistencyException;

public abstract class ExecutionContextFactory {

    public abstract ExecutionContext createExecutionContext(CliCall cliCall) throws InconsistencyException;

}
