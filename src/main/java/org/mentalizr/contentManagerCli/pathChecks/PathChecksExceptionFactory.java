package org.mentalizr.contentManagerCli.pathChecks;

import java.nio.file.Path;

public interface PathChecksExceptionFactory<E extends Exception> {

    E createException(String message);

}
