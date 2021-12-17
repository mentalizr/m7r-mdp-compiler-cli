package org.mentalizr.contentManagerCli.consistency;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NoOrphanedMediaResourcesValidatorTest {

    @Test
    void isConsistent() {
        Set<String> referencedMediaResources = Sets.newHashSet("A", "B", "C");
        Set<String> availableMediaResources = Sets.newHashSet("A", "B", "C");

        NoOrphanedMediaResourcesValidator noOrphanedMediaResourcesValidator
                = new NoOrphanedMediaResourcesValidator(referencedMediaResources, availableMediaResources);

        assertTrue(noOrphanedMediaResourcesValidator.isConsistent());
        assertFalse(noOrphanedMediaResourcesValidator.hasOrphanedMediaResources());
        assertTrue(noOrphanedMediaResourcesValidator.getOrphanedMediaResources().isEmpty());
    }

    @Test
    void orphaned() {
        Set<String> referencedMediaResources = Sets.newHashSet("A", "B");
        Set<String> availableMediaResources = Sets.newHashSet("A", "B", "C");

        NoOrphanedMediaResourcesValidator noOrphanedMediaResourcesValidator
                = new NoOrphanedMediaResourcesValidator(referencedMediaResources, availableMediaResources);

        assertFalse(noOrphanedMediaResourcesValidator.isConsistent());
        assertTrue(noOrphanedMediaResourcesValidator.hasOrphanedMediaResources());
        assertFalse(noOrphanedMediaResourcesValidator.getOrphanedMediaResources().isEmpty());
        assertEquals(1, noOrphanedMediaResourcesValidator.getOrphanedMediaResources().size());
        assertTrue(noOrphanedMediaResourcesValidator.getOrphanedMediaResources().contains("C"));
    }

}