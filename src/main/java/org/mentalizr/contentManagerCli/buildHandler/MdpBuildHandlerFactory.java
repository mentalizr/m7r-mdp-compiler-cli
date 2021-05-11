package org.mentalizr.contentManagerCli.buildHandler;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.buildHandler.BuildHandler;
import org.mentalizr.contentManager.buildHandler.BuildHandlerFactory;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.mdpCompiler.Const;

public class MdpBuildHandlerFactory extends BuildHandlerFactory {

    @Override
    public BuildHandler createBuildHandler(Program program, MdpFile mdpFile) {
        return new MdpBuildHandler(program, mdpFile);
    }

    @Override
    public String getCompilerVersion() {
        return Const.VERSION + " from " + Const.VERSION_DATE;
    }

}
