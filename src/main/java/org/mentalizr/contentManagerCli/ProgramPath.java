package org.mentalizr.contentManagerCli;

import java.nio.file.Path;

public class ProgramPath {

    private final Path programPath;

    public ProgramPath(Path programPath) {
        this.programPath = programPath;
    }

    public Path getPath() {
        return programPath;
    }

    public String getProgramName() {
        return this.programPath.getFileName().toString();
    }

    @Override
    public String toString() {
        return this.programPath.toString();
    }

}
