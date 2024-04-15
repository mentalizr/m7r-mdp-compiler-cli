package org.mentalizr.contentManagerCli.build.cleanBuild;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ValidationException;
import org.mentalizr.contentManager.validator.*;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.BuildSummary;
import org.mentalizr.contentManagerCli.compileProgram.CompileProgram;
import org.mentalizr.contentManagerCli.compileProgram.CompileProgramResult;
import org.mentalizr.contentManagerCli.consistency.NoOrphanedMediaResourcesValidator;
import org.mentalizr.contentManagerCli.consistency.ProgramLogoValidator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CleanBuild {

    private final ProgramPath programPath;

    public CleanBuild(ProgramPath programPath) {
        this.programPath = programPath;
    }

    public BuildSummary execute() throws ContentManagerException {

        Program program;
        try {
            program = new Program(programPath.getPath());
        } catch (ValidationException e) {
            return BuildSummary.getInstanceOnValidationException(e);
        }

        program.cleanHtmlDir();
        program.createHtmlDirSkeleton();

        ProgramLogoValidator.validate(program);

        CompileProgramResult compileProgramResult = new CompileProgram(program).execute();

        Set<String> referencedMediaResources = compileProgramResult.getReferencedMediaResources();
        if (Strings.isSpecified(program.getProgramConf().getLogo())) {
            referencedMediaResources.add(program.getProgramConf().getLogo());
        }

        NoOrphanedMediaResourcesValidator noOrphanedMediaResourcesValidator
                = NoOrphanedMediaResourcesValidator.create(program, compileProgramResult.getReferencedMediaResources());

        BuildSummary buildSummary = BuildSummary.getInstance(
                compileProgramResult.getSuccessfulMdpFiles(),
                compileProgramResult.getFailedMdpFiles(),
                noOrphanedMediaResourcesValidator.getOrphanedMediaResources()
        );

        if (!buildSummary.isSuccess()) {
            program.cleanHtmlDir();
        } else {
            program.reinitializeProgramDir();
        }

        return buildSummary;
    }

}
