package org.mentalizr.contentManagerCli;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.helper.PathAssertions;
import org.mentalizr.contentManagerCli.executors.ExecutionSummary;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mentalizr.contentManagerCli.ContentManagerCli.*;

public class ExecutionContext {

    private final Path contentRootPath;
    private final boolean verbose;
    private final boolean stacktrace;

    public ExecutionContext(CliCall cliCall) throws ContentManagerCliException {

        if (cliCall.getOptionParserResultGlobal().hasOption(OPTION_CONTENT_ROOT)) {
            String contentRoot = cliCall.getOptionParserResultGlobal().getValue(OPTION_CONTENT_ROOT);
            this.contentRootPath = Paths.get(contentRoot).toAbsolutePath();
            PathAssertions.assertIsExistingDirectory(this.contentRootPath);
        } else {
            this.contentRootPath = Path.of("").toAbsolutePath();
        }

        this.verbose = cliCall.getOptionParserResultGlobal().hasOption(OPTION_VERBOSE);
        this.stacktrace = cliCall.getOptionParserResultGlobal().hasOption(OPTION_STACKTRACE);
    }

    public Path getContentRootPath() {
        return this.contentRootPath;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public boolean isStacktrace() {
        return this.stacktrace;
    }

}
