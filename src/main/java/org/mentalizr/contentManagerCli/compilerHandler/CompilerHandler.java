package org.mentalizr.contentManagerCli.compilerHandler;

import org.mentalizr.contentManager.fileHierarchy.exceptions.MediaNotFoundException;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.util.Set;


public interface CompilerHandler {

    CompilerHandlerResult compile() throws CompilerHandlerException, MDPSyntaxError, MediaNotFoundException;

    Set<String> getReferencedMediaResources() throws CompilerHandlerException;

}
