package org.mentalizr.contentManagerCli.consistency;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;

import java.util.Set;

public class ProgramLogoValidator {

    public static void validate(Program program) throws ContentManagerException {
        String logo = program.getProgramConf().getLogo();
        if (Strings.isUnspecified(logo)) return;
        Set<String> availableMediaResources = program.getAllMediaResourceNames();
        if (!availableMediaResources.contains(logo))
            throw new ContentManagerException(
                    "Configured logo [" + logo + "] for program [" + program.getName() + "] not found."
            );
    }

}
