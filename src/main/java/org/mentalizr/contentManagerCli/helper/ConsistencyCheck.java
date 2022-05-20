package org.mentalizr.contentManagerCli.helper;

import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;

public class ConsistencyCheck {

//    public static void assertPathEndsWithFileName(Path file, String fileName) {
//        if (!Nio2Helper.isPathEndingWithFileName(file, fileName))
//            throw new CommandExecutorException("Assertion failed. File name [" + fileName + "] expected " +
//                    "but is [" + file.getFileName() + "].");
//    }

    public static void assertIsExistingDirectory(Path dir) throws CommandExecutorException {
        if (!FileUtils.isExistingDirectory(dir))
            throw new CommandExecutorException("Directory not existing: ["
                    + dir.toAbsolutePath().toString() + "].");
    }

//    public static void assertPathNotExisting(Path path) {
//        if (Files.exists(path))
//            throw new PathAssertionException(path, "Assertion failed. Path as not existing expected : ["
//                    + path.toAbsolutePath().toString() + "].");
//    }

//    public static void assertIsDirectSubdirectory(Path dir, Path subdir) {
//        if (!Nio2Helper.isDirectSubdirectory(dir, subdir))
//            throw new CommandExecutorException(subdir, "Assertion failed. [" + subdir.toAbsolutePath().toString() + "] " +
//                    "is no direct subdirectory of [" + dir.toAbsolutePath().toString() + "].");
//    }


}
