package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.exceptions.InconsistencyException;
import org.mentalizr.contentManager.helper.Nio2Helper;
import org.mentalizr.contentManager.helper.PathConsistencyCheck;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mentalizr.contentManagerCli.ContentManagerCli.*;

public class ExecutionContext {

    private final Path contentRootPath;
    private final boolean verbose;
    private final boolean omitSummary;
    private final boolean stacktrace;

    public ExecutionContext(CliCall cliCall) throws InconsistencyException {
        this.contentRootPath = this.obtainContentRootPath(cliCall);
        this.verbose = cliCall.getOptionParserResultGlobal().hasOption(OPTION_VERBOSE);
        this.omitSummary = cliCall.getOptionParserResultGlobal().hasOption(OPTION_NO_SUMMARY);
        this.stacktrace = cliCall.getOptionParserResultGlobal().hasOption(OPTION_STACKTRACE);
    }

    private Path obtainContentRootPath(CliCall cliCall) throws InconsistencyException {
        if (cliCall.getOptionParserResultGlobal().hasOption(OPTION_CONTENT_ROOT)) {
            return obtainUserDefinedContentRootPath(cliCall);
        } else {
            return Nio2Helper.getCurrentWorkingDir();
        }
    }

    private Path obtainUserDefinedContentRootPath(CliCall cliCall) throws InconsistencyException {
        String contentRoot = cliCall.getOptionParserResultGlobal().getValue(OPTION_CONTENT_ROOT);
        Path contentRootPath =  Paths.get(contentRoot).toAbsolutePath();
        PathConsistencyCheck.assertIsExistingDirectory(contentRootPath);
        return contentRootPath;
    }

    public Path getContentRootPath() {
        return this.contentRootPath;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public boolean isOmitSummary() {
        return omitSummary;
    }

    public boolean isStacktrace() {
        return this.stacktrace;
    }

}
