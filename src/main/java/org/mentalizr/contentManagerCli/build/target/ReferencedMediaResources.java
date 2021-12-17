package org.mentalizr.contentManagerCli.build.target;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.compilerHandler.CompilerHandler;
import org.mentalizr.contentManagerCli.compilerHandler.CompilerHandlerException;
import org.mentalizr.contentManagerCli.compilerHandler.MdpCompilerHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReferencedMediaResources {

    public static Set<String> get(Program program) throws ContentManagerException {
        if (!program.hasHtmlDir()) throw new RuntimeException("Program not built yet. Check before calling.");

        List<MdpFile> mdpFiles = program.getProgramDir().getMdpFiles();
        Set<String> referencesMediaResources = new HashSet<>();

        for (MdpFile mdpFile : mdpFiles) {
            Set<String> mediaFilesOfSingleMdpFile;
            try {
                CompilerHandler compilerHandler = new MdpCompilerHandler(program, mdpFile);
                mediaFilesOfSingleMdpFile = compilerHandler.getReferencedMediaResources();
            } catch (CompilerHandlerException e) {
                throw new ContentManagerException(e);
            }
            referencesMediaResources.addAll(mediaFilesOfSingleMdpFile);
        }

        return referencesMediaResources;
    }


}
