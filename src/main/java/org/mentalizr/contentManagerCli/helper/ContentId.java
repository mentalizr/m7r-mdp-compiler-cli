package org.mentalizr.contentManagerCli.helper;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManager.fileHierarchy.levels.contentRoot.MdpDir;
import org.mentalizr.contentManager.fileHierarchy.levels.info.InfoDir;

import java.nio.file.Path;

public class ContentId {

    private static final String infoDelimiter = "__info_";
    private static final String delimiter = "_";

    private final String contentId;
    private final String programId;
    private final String infoId;
    private final String moduleId;
    private final String submoduleId;
    private final String stepId;

    public ContentId(String contentId) throws ContentIdException {
        this.contentId = contentId;

        if (contentId.contains(infoDelimiter)) {
            String[] splitString = Strings.splitAtDelimiter(contentId, infoDelimiter);
            if (splitString[0].equals("") || splitString[1].equals("")) throw new ContentIdException();
            this.programId = splitString[0];
            this.infoId = splitString[1];

            this.moduleId = "";
            this.submoduleId = "";
            this.stepId = "";
        } else {

            this.programId = getNextId(contentId);
            contentId = contentId.substring(this.programId.length() + 1);

            this.moduleId = getNextId(contentId);
            contentId = contentId.substring(this.moduleId.length() + 1);

            this.submoduleId = getNextId(contentId);
            contentId = contentId.substring(this.submoduleId.length() + 1);

            this.stepId = contentId;

            this.infoId = "";
        }
    }

    public String getContentId() {
        return contentId;
    }

    public String getProgramId() {
        return programId;
    }

    public String getInfoId() {
        return infoId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getSubmoduleId() {
        return submoduleId;
    }

    public String getStepId() {
        return stepId;
    }

    public boolean isInfo() {
        return !isStep();
    }

    public boolean isStep() {
        return this.infoId.equals("");
    }

    public Path getPathToMdpFile(Path contentRoot) {
        if (this.isInfo()) {
            return contentRoot.resolve(this.programId).resolve(InfoDir.DIR_NAME).resolve(this.infoId + MdpFile.FILETYPE);
        } else {
            return contentRoot.resolve(this.programId).resolve(MdpDir.DIR_NAME).resolve(this.moduleId).resolve(this.submoduleId).resolve(this.stepId + MdpFile.FILETYPE);
        }
    }

    private String getNextId(String string) throws ContentIdException {
        if (!string.contains(delimiter)) throw new ContentIdException();
        String[] splitString = Strings.splitAtDelimiter(string, delimiter);
        if (splitString[0].equals("")) throw new ContentIdException();
        return splitString[0];
    }

}
