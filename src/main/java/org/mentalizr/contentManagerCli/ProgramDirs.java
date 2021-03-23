package org.mentalizr.contentManagerCli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgramDirs {

    public static Path getProgramDir(Path contentRootDir, String programName) throws ContentManagerCliException {

        Nio2Helper.assertExistingDir(contentRootDir);

        if (Paths.get(programName).getNameCount() != 1)
            throw new ContentManagerCliException("Illegal program name: [" + programName + "]");

        Path programPath = contentRootDir.resolve(programName);

        Nio2Helper.assertExistingDir(programPath);

        return programPath;
    }

    public static List<Path> getAllProgrammDirs(Path contentRootDir) {

        // TODO


        return new ArrayList<>();


    }

}
