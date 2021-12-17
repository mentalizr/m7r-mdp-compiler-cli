package org.mentalizr.contentManagerCli.build;

import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;

import java.util.Objects;

public class FailedMdpFile {

    private final MdpFile mdpFile;
    private final Exception exception;

    public FailedMdpFile(MdpFile mdpFile, Exception exception) {
        this.mdpFile = mdpFile;
        this.exception = exception;
    }

    public MdpFile getMdpFile() {
        return mdpFile;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FailedMdpFile failedMdpFile = (FailedMdpFile) o;
        return mdpFile.equals(failedMdpFile.mdpFile) && exception.equals(failedMdpFile.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mdpFile, exception);
    }
}
