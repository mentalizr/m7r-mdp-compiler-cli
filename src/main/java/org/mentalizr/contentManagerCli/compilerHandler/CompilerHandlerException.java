package org.mentalizr.contentManagerCli.compilerHandler;

import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;

public class CompilerHandlerException extends Exception{

    private final MdpFile mdpFile;

    public CompilerHandlerException(MdpFile mdpFile, String message) {
        super(message);
        this.mdpFile = mdpFile;
    }

    public CompilerHandlerException(MdpFile mdpFile, Throwable cause) {
        super(cause.getMessage(), cause);
        this.mdpFile = mdpFile;
    }

    public MdpFile getMdpFile() {
        return this.mdpFile;
    }

}
