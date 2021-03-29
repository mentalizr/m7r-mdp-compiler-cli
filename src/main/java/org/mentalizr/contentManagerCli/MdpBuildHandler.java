package org.mentalizr.contentManagerCli;

import org.mentalizr.contentManager.build.BuildException;
import org.mentalizr.contentManager.build.BuildHandler;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.mdpCompiler.MDPCompiler;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MdpBuildHandler implements BuildHandler {

    @Override
    public List<String> compile(MdpFile mdpFile) throws BuildException {

        try {
            System.out.println("Processing: " + mdpFile.asPath().toAbsolutePath());
//            List<String> contentStrings = new ArrayList<>();
//            contentStrings.add("<--");
//            contentStrings.add(mdpFile.asPath().toAbsolutePath().toString());
//            contentStrings.add("-->");
//            return contentStrings;

            return MDPCompiler.compile(mdpFile.asFile());
        } catch (IOException e) {
            throw new BuildException(mdpFile.asPath().toAbsolutePath(), e);
        } catch (MDPSyntaxError mdpSyntaxError) {
            throw new BuildException(mdpFile.asPath().toAbsolutePath(), mdpSyntaxError);
        }
    }

}
