package org.mentalizr.contentManagerCli.compileProgram;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.build.FailedMdpFile;

import java.util.List;
import java.util.Set;

public final class CompileProgramResult {

    private final Program program;
    private final List<MdpFile> successfulMdpFiles;
    private final List<FailedMdpFile> failedMdpFiles;
    private final Set<String> referencedMediaResources;

    public CompileProgramResult(
            Program program,
            List<MdpFile> successfulMdpFiles,
            List<FailedMdpFile> failedMdpFiles,
            Set<String> referencedMediaResources) {

        this.program = program;
        this.successfulMdpFiles = successfulMdpFiles;
        this.failedMdpFiles = failedMdpFiles;
        this.referencedMediaResources = referencedMediaResources;
    }

    public Program getProgram() {
        return program;
    }

    public List<MdpFile> getSuccessfulMdpFiles() {
        return successfulMdpFiles;
    }

    public List<FailedMdpFile> getFailedMdpFiles() {
        return failedMdpFiles;
    }

    public Set<String> getReferencedMediaResources() {
        return referencedMediaResources;
    }

    public boolean hasFails() {
        return !this.failedMdpFiles.isEmpty();
    }
}
