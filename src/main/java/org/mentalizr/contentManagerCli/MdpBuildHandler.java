package org.mentalizr.contentManagerCli;

import org.mentalizr.contentManager.build.BuildException;
import org.mentalizr.contentManager.build.BuildHandler;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.mdpCompiler.MDPCompiler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MdpBuildHandler implements BuildHandler {

    @Override
    public List<String> compile(MdpFile mdpFile) throws BuildException {
        try {
            return MDPCompiler.compile(mdpFile.asFile());
        } catch (IOException | MDPSyntaxError e) {
            throw new BuildException(mdpFile.asPath().toAbsolutePath(), e);
        }
    }

    @Override
    public Set<String> getReferencedMediaResources(MdpFile mdpFile) throws BuildException {
        try {
            return MDPCompiler.getReferencedMediaResources(mdpFile.asFile());
        } catch (IOException | MDPSyntaxError e) {
            throw new BuildException(mdpFile.asPath().toAbsolutePath(), e);
        }
    }

}
