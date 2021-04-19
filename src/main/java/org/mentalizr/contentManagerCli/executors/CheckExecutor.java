package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManager.exceptions.ConsistencyException;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.console.Console;
import org.mentalizr.contentManagerCli.helper.ContentId;
import org.mentalizr.contentManagerCli.helper.ContentIdException;
import org.mentalizr.mdpCompiler.MDPCompiler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CheckExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {
        ExecutionContext executionContext = initExecutionContext(cliCall);
        String contentIdString = getParameter(cliCall);
        ContentId contentId = obtainContentId(contentIdString);
        MdpFile mdpFile = obtainMdpFile(executionContext, contentId);
        checkMdpFile(executionContext, mdpFile);
    }

    private ExecutionContext initExecutionContext(CliCall cliCall) throws CommandExecutorException {
        try {
            return new ExecutionContext(cliCall);
        } catch (ConsistencyException e) {
            throw new CommandExecutorException(e.getMessage(), e);
        }
    }

    private String getParameter(CliCall cliCall) {
        List<String> parameterList = cliCall.getParameterList();
        if (parameterList.isEmpty()) throw new RuntimeException("Specified for one parameter.");
        return parameterList.get(0);
    }

    private ContentId obtainContentId(String contentIdString) throws CommandExecutorException {
        try {
            return new ContentId(contentIdString);
        } catch (ContentIdException e) {
            Console.errorOut("Content ID not valid.");
            throw new CommandExecutorException(e);
        }
    }

    private MdpFile obtainMdpFile(ExecutionContext executionContext, ContentId contentId) throws CommandExecutorException {

        Path mdpFile = contentId.getPathToMdpFile(executionContext.getContentRootPath());
        try {
            return new MdpFile(mdpFile.toFile());
        } catch (ContentManagerException e) {
            Console.errorOut("MDP-file for content ID [" + contentId.getContentId() + "] not found: [" + mdpFile.toAbsolutePath() + "]");
            throw new CommandExecutorException(e);
        }
    }

    private void checkMdpFile(ExecutionContext executionContext, MdpFile mdpFile) throws CommandExecutorException {
        try {
            MDPCompiler.compile(mdpFile.asFile());
            Console.okOut("[" + mdpFile.asPath().toAbsolutePath() + "]");
        } catch (IOException e) {
            if (executionContext.isStacktrace()) e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MDPSyntaxError mdpSyntaxError) {
            Console.errorOut(MDPSyntaxError.getExtendedMessage(mdpFile.asPath(), mdpSyntaxError));
            if (executionContext.isStacktrace()) mdpSyntaxError.printStackTrace();
            throw new CommandExecutorException(mdpSyntaxError);
        }
    }

}
