package org.mentalizr.contentManagerCli;

import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.io.assertions.PathAssertions;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.contentManager.fileHierarchy.basics.Naming;
import org.mentalizr.contentManagerCli.helper.ConsistencyCheck;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProgramDirs {

    public static List<ProgramPath> getProgramDirs(Path contentRootDir, List<String> programNames) throws CommandExecutorException {
        List<ProgramPath> programPaths = new ArrayList<>();
        for (String programName : programNames) {
            Path programDir = getProgramDir(contentRootDir, programName);
            programPaths.add(new ProgramPath(programDir));
        }
        return programPaths;
    }

    public static Path getProgramDir(Path contentRootDir, String programName) throws CommandExecutorException {
        assertValidProgramName(programName);
        Path programPath = contentRootDir.resolve(programName);
        if (!FileUtils.isExistingDirectory(programPath))
            throw new CommandExecutorException("Program directory not existing: [" + programPath + "].");
        PathAssertions.assertIsDirectSubdirectory(contentRootDir, programPath);
        return programPath;
    }

    public static List<ProgramPath> getAllProgramPaths(Path contentRootDir) throws CommandExecutorException {
        ConsistencyCheck.assertIsExistingDirectory(contentRootDir);
        return getSubdirectoriesAsCheckedProgramPath(contentRootDir);
    }

    private static List<ProgramPath> getSubdirectoriesAsCheckedProgramPath(Path dir) throws CommandExecutorException {
        List<ProgramPath> programPaths = new ArrayList<>();
        try {
            List<Path> programDirs = FileUtils.getSubdirectoriesNotEndingWithTilde(dir);
            for (Path programDir : programDirs) {
                String programName = programDir.getFileName().toString();
                assertValidProgramName(programName);
                programPaths.add(new ProgramPath(programDir));
            }
            return programPaths;
        } catch (IOException e) {
            throw new CommandExecutorException("Error when accessing file system. " + e.getMessage(), e);
        }
    }

    private static void assertValidProgramName(String programName) throws CommandExecutorException {
        if (!Naming.isValidProgramName(programName))
            throw new CommandExecutorException("No valid program name: '" + programName + "'.");
    }

}
