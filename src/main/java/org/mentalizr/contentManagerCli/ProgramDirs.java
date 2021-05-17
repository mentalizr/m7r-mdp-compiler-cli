package org.mentalizr.contentManagerCli;

import de.arthurpicht.cli.CommandExecutorException;
import org.mentalizr.contentManager.fileHierarchy.basics.Naming;
import org.mentalizr.contentManager.helper.Nio2Helper;
import org.mentalizr.contentManager.helper.PathAssertions;
import org.mentalizr.contentManagerCli.helper.ConsistencyCheck;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProgramDirs {

    public static List<Path> getProgramDirs(Path contentRootDir, List<String> programNames) throws CommandExecutorException {
        List<Path> programPaths = new ArrayList<>();
        for (String programName : programNames) {
            programPaths.add(getProgramDir(contentRootDir, programName));
        }
        return programPaths;
    }

    public static Path getProgramDir(Path contentRootDir, String programName) throws CommandExecutorException {
        assertValidProgramName(programName);
        Path programPath = contentRootDir.resolve(programName);
        if (!Nio2Helper.isExistingDir(programPath))
            throw new CommandExecutorException("Program directory not existing: [" + programPath + "].");
        PathAssertions.assertIsDirectSubdirectory(contentRootDir, programPath);
        return programPath;
    }

    public static List<Path> getAllProgramDirs(Path contentRootDir) throws CommandExecutorException {
        ConsistencyCheck.assertIsExistingDirectory(contentRootDir);
        return getCheckedSubdirectories(contentRootDir);
    }

    private static List<Path> getCheckedSubdirectories(Path dir) throws CommandExecutorException {
        try {
            List<Path> programDirs = Nio2Helper.getActiveSubdirectories(dir);
            for (Path programDir : programDirs) {
                String programName = programDir.getFileName().toString();
                assertValidProgramName(programName);
            }
            return programDirs;
        } catch (IOException e) {
            throw new CommandExecutorException("Error when accessing file system. " + e.getMessage(), e);
        }
    }

    private static void assertValidProgramName(String programName) throws CommandExecutorException {
        if (!Naming.isValidProgramName(programName))
            throw new CommandExecutorException("No valid program name: '" + programName + "'.");
    }

}
