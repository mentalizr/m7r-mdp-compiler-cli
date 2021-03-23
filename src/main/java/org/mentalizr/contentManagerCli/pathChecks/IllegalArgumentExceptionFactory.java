package org.mentalizr.contentManagerCli.pathChecks;

import java.nio.file.Path;

public class IllegalArgumentExceptionFactory implements PathChecksExceptionFactory<IllegalArgumentException> {

    public IllegalArgumentException createException(String message) {
        return new IllegalArgumentException();
    }

}
