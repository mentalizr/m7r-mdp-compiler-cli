package org.mentalizr.contentManagerCli.build;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.build.cleanBuild.CleanBuild;
import org.mentalizr.contentManagerCli.build.target.ReferencedMediaResources;

import java.nio.file.Path;
import java.util.Set;

public class Build {

    public static BuildSummary cleanBuild(ProgramPath programPath) throws ContentManagerException {
        return new CleanBuild(programPath).execute();
    }

    public static Set<String> getReferencesMediaResources(Program program) throws ContentManagerException {
        return ReferencedMediaResources.get(program);
    }


}
