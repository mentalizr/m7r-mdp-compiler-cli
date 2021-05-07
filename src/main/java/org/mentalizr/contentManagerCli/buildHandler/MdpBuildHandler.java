package org.mentalizr.contentManagerCli.buildHandler;

import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.buildHandler.BuildHandler;
import org.mentalizr.contentManager.buildHandler.BuildHandlerException;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.exceptions.MediaResourceReferenceInconsistencyException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManager.helper.SetHelper;
import org.mentalizr.mdpCompiler.Dom;
import org.mentalizr.mdpCompiler.MDPCompiler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MdpBuildHandler implements BuildHandler {

    private final Program program;
    private final MdpFile mdpFile;

    public MdpBuildHandler(Program program, MdpFile mdpFile) {
        this.program = program;
        this.mdpFile = mdpFile;
    }

    @Override
    public List<String> compile() throws BuildHandlerException {
        Dom dom = createDom();
        checkMediaReferenceIntegrity(dom);
        return MDPCompiler.render(dom).getResultLines();
    }

    @Override
    public Set<String> getReferencedMediaResources() throws BuildHandlerException {
        try {
            return MDPCompiler.getReferencedMediaResources(this.mdpFile.asFile());
        } catch (IOException | MDPSyntaxError e) {
            throw new BuildHandlerException(this.mdpFile, e);
        }
    }

    private Dom createDom() throws BuildHandlerException {
        try {
            return MDPCompiler.createDom(this.mdpFile.asFile());
        } catch (IOException | MDPSyntaxError e) {
            throw new BuildHandlerException(this.mdpFile, e);
        }
    }

    private void checkMediaReferenceIntegrity(Dom dom) throws BuildHandlerException {
        Set<String> referencedMediaResources = dom.getReferencedMediaResources();
        try {
            Set<String> availableMediaResources = this.program.getAllMediaResourceNames();
            Set<String> deReferencedMediaFiles = SetHelper.subtract(referencedMediaResources, availableMediaResources);
            if (!deReferencedMediaFiles.isEmpty()) {
                String mediaFile = Sets.getSomeElement(deReferencedMediaFiles);
                throw new MediaResourceReferenceInconsistencyException(mediaFile);
            }
        } catch (ContentManagerException e) {
            throw new BuildHandlerException(this.mdpFile, e);
        }
    }

}
