package com.cmsApp.cms;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.validation.ContentValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContentValidationTest {

    @Test
    void addingOverlappingLicenseThrowsException() {
        ContentValidation validation = new ContentValidation();
        Content content = new Content();

        License l1 = new License();
        l1.setStartTime(0L);
        l1.setEndTime(1000L);
        content.addLicenseToContent(l1);

        License overlapping = new License();
        overlapping.setStartTime(1000L); // starts exactly when previous ends
        overlapping.setEndTime(2000L);

        assertThrows(TimeWindowException.class,
                () -> validation.isLicenseValidForContent(content, overlapping));
    }

    @Test
    void overlapAtStartThrowsException() {
        ContentValidation validation = new ContentValidation();
        Content content = new Content();

        License existing = new License();
        existing.setStartTime(100L);
        existing.setEndTime(200L);
        content.addLicenseToContent(existing);

        License newLicense = new License();
        newLicense.setStartTime(50L);
        newLicense.setEndTime(150L);

        assertThrows(TimeWindowException.class,
                () -> validation.isLicenseValidForContent(content, newLicense));
    }

    @Test
    void overlapInsideThrowsException() {
        ContentValidation validation = new ContentValidation();
        Content content = new Content();

        License existing = new License();
        existing.setStartTime(100L);
        existing.setEndTime(200L);
        content.addLicenseToContent(existing);

        License newLicense = new License();
        newLicense.setStartTime(120L);
        newLicense.setEndTime(180L);

        assertThrows(TimeWindowException.class,
                () -> validation.isLicenseValidForContent(content, newLicense));
    }

    @Test
    void overlapAtEndThrowsException() {
        ContentValidation validation = new ContentValidation();
        Content content = new Content();

        License existing = new License();
        existing.setStartTime(100L);
        existing.setEndTime(200L);
        content.addLicenseToContent(existing);

        License newLicense = new License();
        newLicense.setStartTime(150L);
        newLicense.setEndTime(250L);

        assertThrows(TimeWindowException.class,
                () -> validation.isLicenseValidForContent(content, newLicense));
    }

    @Test
    void nonOverlappingLicenseSucceeds() throws TimeWindowException {
        ContentValidation validation = new ContentValidation();
        Content content = new Content();

        License existing = new License();
        existing.setStartTime(100L);
        existing.setEndTime(200L);
        content.addLicenseToContent(existing);

        License newLicense = new License();
        newLicense.setStartTime(250L);
        newLicense.setEndTime(300L);

        assertTrue(validation.isLicenseValidForContent(content, newLicense));
    }
}
