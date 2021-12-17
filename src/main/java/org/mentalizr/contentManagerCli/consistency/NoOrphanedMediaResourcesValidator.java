package org.mentalizr.contentManagerCli.consistency;

import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.helper.SetHelper;

import java.util.Set;

public class NoOrphanedMediaResourcesValidator {

    private final Set<String> orphanedMediaResources;

    public static NoOrphanedMediaResourcesValidator create(Program program, Set<String> referencedMediaResources) throws ContentManagerException {
        Set<String> availableMediaResources = program.getAllMediaResourceNames();
        return new NoOrphanedMediaResourcesValidator(referencedMediaResources, availableMediaResources);
    }

    public NoOrphanedMediaResourcesValidator(Set<String> referencedMediaResources, Set<String> availableMediaResources) {
        this.orphanedMediaResources = SetHelper.subtract(availableMediaResources, referencedMediaResources);
    }

    public boolean isConsistent() {
        return !hasOrphanedMediaResources();
    }

    public boolean hasOrphanedMediaResources() {
        return !this.orphanedMediaResources.isEmpty();
    }

    public Set<String> getOrphanedMediaResources() {
        return this.orphanedMediaResources;
    }

}
