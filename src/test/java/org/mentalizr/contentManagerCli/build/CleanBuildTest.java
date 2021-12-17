package org.mentalizr.contentManagerCli.build;

import de.arthurpicht.utils.io.tempDir.TempDir;
import de.arthurpicht.utils.io.tempDir.TempDirs;
import org.junit.jupiter.api.Test;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.fileHierarchy.levels.info.InfoDir;
import org.mentalizr.contentManager.helper.ProgramStubs;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.TestConfig;
import org.mentalizr.contentManagerCli.build.cleanBuild.CleanBuild;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CleanBuildTest {

    @Test
    void build() throws IOException, ContentManagerException {

        // prepare
        TempDir tempDir = TempDirs.createUniqueTempDirAutoRemove(TestConfig.PROJECT_TEMP_DIR);
        Path programRootPath = tempDir.asPath();
        ProgramStubs.createTestProgram(programRootPath);
        ProgramPath programPath = new ProgramPath(programRootPath);

        // execute
        BuildSummary buildSummary = new CleanBuild(programPath).execute();

        // assert
        Program program = new Program(programRootPath.resolve("test"));
        Path htmlPath = program.getProgramDir().getHtmlDir().asPath();

        assertTrue(Files.exists(htmlPath.resolve(InfoDir.DIR_NAME).resolve("info01.html")));
        assertTrue(Files.exists(htmlPath.resolve("m1").resolve("sm1").resolve("step01.html")));
        assertTrue(Files.exists(htmlPath.resolve("m1").resolve("sm1").resolve("step02.html")));
        assertTrue(Files.exists(htmlPath.resolve("m1").resolve("sm2").resolve("step01.html")));
        assertTrue(Files.exists(htmlPath.resolve("m1").resolve("sm2").resolve("step02.html")));
        assertTrue(Files.exists(htmlPath.resolve("m2").resolve("sm1").resolve("step01.html")));

        assertEquals(0, buildSummary.getNrOfFailedMdpFiles());
        assertEquals(6, buildSummary.getNrOfSuccessfulMdpFiles());
    }

}