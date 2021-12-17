package org.mentalizr.contentManagerCli.compileProgram;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.exceptions.MediaNotFoundException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManagerCli.build.FailedMdpFile;
import org.mentalizr.contentManagerCli.compilerHandler.CompilerHandlerException;
import org.mentalizr.contentManagerCli.compilerHandler.CompilerHandlerResult;
import org.mentalizr.contentManagerCli.compilerHandler.MdpCompilerHandler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompileProgram {

    private final Program program;
    private final List<MdpFile> successfulMdpFiles = new ArrayList<>();
    private final List<FailedMdpFile> failedMdpFiles = new ArrayList<>();
    private final Set<String> referencedMediaResources = new HashSet<>();

    public CompileProgram(Program program) {
        this.program = program;
    }

    public CompileProgramResult execute() {
        List<MdpFile> mdpFiles = this.program.getMdpFiles();
        for (MdpFile mdpFile : mdpFiles) {
            compileMdpFile(mdpFile);
        }
        return new CompileProgramResult(
                this.program,
                this.successfulMdpFiles,
                this.failedMdpFiles,
                this.referencedMediaResources
        );
    }

    private void compileMdpFile(MdpFile mdpFile) {
        try {
            CompilerHandlerResult compilerHandlerResult = compileMdpFileUnchecked(mdpFile);
            this.successfulMdpFiles.add(mdpFile);
            this.referencedMediaResources.addAll(compilerHandlerResult.getMediaResources());
        } catch (MDPSyntaxError | CompilerHandlerException | ContentManagerException | MediaNotFoundException e) {
            this.failedMdpFiles.add(new FailedMdpFile(mdpFile, e));
        }
    }

    private CompilerHandlerResult compileMdpFileUnchecked(MdpFile mdpFile) throws CompilerHandlerException, MDPSyntaxError, ContentManagerException, MediaNotFoundException {
        MdpCompilerHandler mdpCompilerHandler = new MdpCompilerHandler(this.program, mdpFile);
        CompilerHandlerResult compilerHandlerResult = mdpCompilerHandler.compile();

        Path mdpDirPath = this.program.getProgramDir().getMdpDir().asPath();
        Path htmlDirPath = this.program.getProgramDir().getHtmlDir().asPath();
        Path htmlFile = Program.getHtmlDestinationForMdpFile(mdpDirPath, htmlDirPath, mdpFile);
        writeAllLines(htmlFile, compilerHandlerResult.getHtml());

        return compilerHandlerResult;
    }

    private void writeAllLines(Path file, List<String> lines) throws ContentManagerException {
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new ContentManagerException(e);
        }
    }

}
