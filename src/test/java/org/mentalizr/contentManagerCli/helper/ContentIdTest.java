package org.mentalizr.contentManagerCli.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentIdTest {

    @Test
    public void stepPositive() throws ContentIdException {
        ContentId contentId = new ContentId("program_m5_sm4_s2");

        assertTrue(contentId.isStep());
        assertFalse(contentId.isInfo());
        assertEquals("program", contentId.getProgramId());
        assertEquals("m5", contentId.getModuleId());
        assertEquals("sm4", contentId.getSubmoduleId());
        assertEquals("s2", contentId.getStepId());
    }

    @Test
    public void infoPositive() throws ContentIdException {
        ContentId contentId = new ContentId("program__info_1");

        assertFalse(contentId.isStep());
        assertTrue(contentId.isInfo());
        assertEquals("program", contentId.getProgramId());
        assertEquals("1", contentId.getInfoId());
    }

    @Test
    public void extraUnderscore() throws ContentIdException {
        ContentId contentId = new ContentId("program_m5_sm4_s2_");

        assertTrue(contentId.isStep());
        assertFalse(contentId.isInfo());
        assertEquals("program", contentId.getProgramId());
        assertEquals("m5", contentId.getModuleId());
        assertEquals("sm4", contentId.getSubmoduleId());
        assertEquals("s2_", contentId.getStepId());
    }

    @Test
    public void missingStep() {
        try {
            new ContentId("program_m5_sm4");
            fail(ContentIdException.class.getSimpleName() + " expected.");
        } catch (ContentIdException e) {
            // din
        }
    }

}