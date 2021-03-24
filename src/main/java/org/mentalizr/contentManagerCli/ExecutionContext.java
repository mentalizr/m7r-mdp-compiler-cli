package org.mentalizr.contentManagerCli;

import de.arthurpicht.cli.CliCall;
import org.mentalizr.contentManager.helper.PathAssertions;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mentalizr.contentManagerCli.ContentManagerCli.OPTION_CONTENT_ROOT;

public class ExecutionContext {

    private final Path contentRootPath;

    public ExecutionContext(CliCall cliCall) throws ContentManagerCliException {

        if (cliCall.getOptionParserResultGlobal().hasOption(OPTION_CONTENT_ROOT)) {
            String contentRoot = cliCall.getOptionParserResultGlobal().getValue(OPTION_CONTENT_ROOT);
            this.contentRootPath = Paths.get(contentRoot).toAbsolutePath();
            PathAssertions.assertIsExistingDirectory(this.contentRootPath);
        } else {
            this.contentRootPath = Path.of("").toAbsolutePath();
        }
    }

    public Path getContentRootPath() {
        return this.contentRootPath;
    }

}
