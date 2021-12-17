package org.mentalizr.contentManagerCli.build;

import org.mentalizr.contentManager.exceptions.ValidationException;
import org.mentalizr.contentManager.fileHierarchy.levels.contentFile.MdpFile;
import org.mentalizr.contentManager.validator.ValidationError;

import java.util.*;

public class BuildSummary {

    private final List<ValidationError> validationErrors;
    private final List<MdpFile> successfulMpdFiles;
    private final List<FailedMdpFile> failedMdpFiles;
    private final Set<String> orphanedMediaResources;

    public static BuildSummary getInstance(
            List<MdpFile> successfulMpdFiles,
            List<FailedMdpFile> failedMdpFiles,
            Set<String> orphanedMediaResources
    ) {
        return new BuildSummary(
                new ArrayList<>(),
                successfulMpdFiles,
                failedMdpFiles,
                orphanedMediaResources);
    }

    public static BuildSummary getInstanceOnValidationException(ValidationException e) {
        return new BuildSummary(e.getValidationErrorList(), new ArrayList<>(), new ArrayList<>(), new HashSet<>());
    }

    private BuildSummary(
            List<ValidationError> validationErrors,
            List<MdpFile> successfulMpdFiles,
            List<FailedMdpFile> failedMdpFiles,
            Set<String> orphanedMediaResources
    ) {
        this.validationErrors = Collections.unmodifiableList(validationErrors);
        this.successfulMpdFiles = Collections.unmodifiableList(successfulMpdFiles);
        this.failedMdpFiles = Collections.unmodifiableList(failedMdpFiles);
        this.orphanedMediaResources = orphanedMediaResources;
    }

    public int getNrOfValidationErrors() {
        return this.validationErrors.size();
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    public boolean hasValidationErrors() {
        return !this.validationErrors.isEmpty();
    }

    public int getNrOfSuccessfulMdpFiles() {
        return this.successfulMpdFiles.size();
    }

    public int getNrOfFailedMdpFiles() {
        return this.failedMdpFiles.size();
    }

    public int getTotalNrOfMdpFiles() {
        return getNrOfSuccessfulMdpFiles() + getNrOfFailedMdpFiles();
    }

    public List<MdpFile> getSuccessfulMpdFiles() {
        return this.successfulMpdFiles;
    }

    public List<FailedMdpFile> getFailedMdpFiles() {
        return this.failedMdpFiles;
    }

    public boolean hasOrphanedMediaResources() {
        return !this.orphanedMediaResources.isEmpty();
    }

    public Set<String> getOrphanedMediaResources() {
        return orphanedMediaResources;
    }

    public int getNrOfOrphanedMediaResources() {
        return this.orphanedMediaResources.size();
    }

    public boolean isSuccess() {
        return this.validationErrors.isEmpty() && this.failedMdpFiles.isEmpty();
    }

}
