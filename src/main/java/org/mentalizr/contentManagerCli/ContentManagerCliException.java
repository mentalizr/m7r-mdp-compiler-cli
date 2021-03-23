package org.mentalizr.contentManagerCli;

public class ContentManagerCliException extends Exception {

    public ContentManagerCliException() {
    }

    public ContentManagerCliException(String message) {
        super(message);
    }

    public ContentManagerCliException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentManagerCliException(Throwable cause) {
        super(cause);
    }

    public ContentManagerCliException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
