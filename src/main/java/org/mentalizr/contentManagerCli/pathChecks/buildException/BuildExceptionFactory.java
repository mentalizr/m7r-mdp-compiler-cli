package org.mentalizr.contentManagerCli.pathChecks.buildException;

import org.mentalizr.contentManager.build.BuildException;
import org.mentalizr.contentManagerCli.pathChecks.PathChecksExceptionFactory;

import java.nio.file.Path;

public class BuildExceptionFactory implements PathChecksExceptionFactory<BuildException> {

    private final Path path;

    public BuildExceptionFactory(Path path) {
        this.path = path;
    }

    public BuildException createException(String message) {
        return new BuildException(this.path, message);
    }

}
