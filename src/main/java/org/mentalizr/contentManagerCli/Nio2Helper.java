package org.mentalizr.contentManagerCli;

import java.nio.file.Files;
import java.nio.file.Path;

public class Nio2Helper {

    public static void assertExistingDir(Path dir) throws ContentManagerCliException {
        if (!Files.exists(dir) || !Files.isDirectory(dir))
            throw new ContentManagerCliException("No existing directory: [" + dir.toAbsolutePath() + "].");
    }

    public static boolean isSubdirectory(Path dir, Path subdir) {
        System.out.println("dir: " + dir.toString());
        Path dirWork = dir.toAbsolutePath();
        System.out.println(dirWork.toString());
        if (!Files.isDirectory(dirWork))
            throw new IllegalArgumentException("Specified path is no directory: [" + dirWork.toAbsolutePath().toString() + "].");

        Path subdirWork = subdir.toAbsolutePath();
        System.out.println(subdirWork.toString());
        if (!Files.isDirectory(subdirWork))
            throw new IllegalArgumentException("Specified path is no directory: [" + subdirWork.toAbsolutePath().toString() + "].");


        return (subdirWork.startsWith(dirWork));
    }

}
