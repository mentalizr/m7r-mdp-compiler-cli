package org.mentalizr.contentManagerCli;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Nio2HelperTest {

    @Test
    public void isSubdir() {

        Path dir = Paths.get("/home/apicht/");
        System.out.println("dir  : " + dir.toString());
        System.out.println("isDir? " + Files.isDirectory(dir));


        Path subDir = Paths.get("/home/dummy/myDir/");

//        assertTrue(Nio2Helper.isSubdirectory(dir, subDir));

    }

}