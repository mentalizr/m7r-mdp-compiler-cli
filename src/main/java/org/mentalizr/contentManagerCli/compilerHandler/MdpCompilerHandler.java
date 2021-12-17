package org.mentalizr.contentManagerCli.compilerHandler;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.exceptions.MediaNotFoundException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManager.helper.SetHelper;
import org.mentalizr.mdpCompiler.Dom;
import org.mentalizr.mdpCompiler.MDPCompiler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MdpCompilerHandler implements CompilerHandler {

    private final Program program;
    private final MdpFile mdpFile;

    public MdpCompilerHandler(Program program, MdpFile mdpFile) {
        this.program = program;
        this.mdpFile = mdpFile;
    }

    @Override
    public CompilerHandlerResult compile() throws CompilerHandlerException, MDPSyntaxError, MediaNotFoundException {
        Dom dom = createDom();
        Set<String> mediaResources = checkMediaReferenceIntegrity(dom);
        List<String> html = MDPCompiler.render(dom).getLines();
        return new CompilerHandlerResult(html, mediaResources);
    }

    @Override
    public Set<String> getReferencedMediaResources() throws CompilerHandlerException {
        try {
            return MDPCompiler.getReferencedMediaResources(this.mdpFile.asFile());
        } catch (IOException | MDPSyntaxError e) {
            throw new CompilerHandlerException(this.mdpFile, e);
        }
    }

    private Dom createDom() throws CompilerHandlerException, MDPSyntaxError {
        try {
            return MDPCompiler.createDom(this.mdpFile.asFile());
        } catch (IOException e) {
            throw new CompilerHandlerException(this.mdpFile, e);
        }
    }

    private Set<String> checkMediaReferenceIntegrity(Dom dom) throws CompilerHandlerException, MediaNotFoundException {
        Set<String> referencedMediaResources = dom.getReferencedMediaResources();
        try {
            Set<String> availableMediaResources = this.program.getAllMediaResourceNames();
            Set<String> deReferencedMediaFiles = SetHelper.subtract(referencedMediaResources, availableMediaResources);
            if (!deReferencedMediaFiles.isEmpty()) {
                throw new MediaNotFoundException(this.mdpFile, deReferencedMediaFiles);
            }
        } catch (ContentManagerException e) {
            throw new CompilerHandlerException(this.mdpFile, e);
        }
        return referencedMediaResources;
    }

}
