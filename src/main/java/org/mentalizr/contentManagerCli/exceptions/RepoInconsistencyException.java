package org.mentalizr.contentManagerCli.exceptions;

public class RepoInconsistencyException extends RuntimeException {

    public RepoInconsistencyException() {
    }

    public RepoInconsistencyException(String message) {
        super(message);
    }

    public RepoInconsistencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoInconsistencyException(Throwable cause) {
        super(cause);
    }

    public RepoInconsistencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
