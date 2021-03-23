package org.mentalizr.contentManagerCli.pathChecks;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathChecks<E extends Exception> {

    public static boolean isExistingDir(Path dir) {
        return (Files.exists(dir) && Files.isDirectory(dir));
    }

    public void assertExistingDir(Path dir, PathChecksExceptionFactory<E> exceptionFactory) throws E {
        if (!isExistingDir(dir)) throw exceptionFactory.createException(
                "Specified path is no existing directory: [" + dir.toAbsolutePath() + "].");
    }


//    public static boolean isSubdirectory(Path dir, Path subdir) {
//        System.out.println("dir: " + dir.toString());
//        Path dirWork = dir.toAbsolutePath();
//        System.out.println(dirWork.toString());
//        if (!Files.isDirectory(dirWork))
//            throw new IllegalArgumentException("Specified path is no directory: [" + dirWork.toAbsolutePath().toString() + "].");
//
//        Path subdirWork = subdir.toAbsolutePath();
//        System.out.println(subdirWork.toString());
//        if (!Files.isDirectory(subdirWork))
//            throw new IllegalArgumentException("Specified path is no directory: [" + subdirWork.toAbsolutePath().toString() + "].");
//
//
//        return (subdirWork.startsWith(dirWork));
//    }

}
