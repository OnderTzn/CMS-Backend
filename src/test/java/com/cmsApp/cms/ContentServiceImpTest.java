package com.cmsApp.cms;

import com.cmsApp.cms.exception.ItemNotFoundException;
import com.cmsApp.cms.repository.ContentRepository;
import com.cmsApp.cms.repository.LicenseRepository;
import com.cmsApp.cms.service.ContentServiceImp;
import com.cmsApp.cms.validation.ContentValidation;
import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.exception.TimeWindowException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ContentServiceImpTest {

    @Test
    void addLicenseWithNonexistentIdsThrows() {
        ContentRepository contentRepository = Mockito.mock(ContentRepository.class);
        LicenseRepository licenseRepository = Mockito.mock(LicenseRepository.class);
        ContentValidation validation = Mockito.mock(ContentValidation.class);
        ContentServiceImp service = new ContentServiceImp(contentRepository, validation, licenseRepository);

        Long contentId = 1L;
        Long licenseId = 1L;

        Mockito.when(contentRepository.findById(contentId)).thenReturn(Optional.empty());
        Mockito.when(licenseRepository.findById(licenseId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> service.addLicenseToContent(contentId, licenseId));
    }

    @Test
    void addLicenseWithMissingContentThrows() {
        ContentRepository contentRepository = Mockito.mock(ContentRepository.class);
        LicenseRepository licenseRepository = Mockito.mock(LicenseRepository.class);
        ContentValidation validation = Mockito.mock(ContentValidation.class);
        ContentServiceImp service = new ContentServiceImp(contentRepository, validation, licenseRepository);

        Long contentId = 1L;
        Long licenseId = 2L;

        Mockito.when(contentRepository.findById(contentId)).thenReturn(Optional.empty());
        Mockito.when(licenseRepository.findById(licenseId)).thenReturn(Optional.of(new com.cmsApp.cms.model.License()));

        assertThrows(ItemNotFoundException.class,
                () -> service.addLicenseToContent(contentId, licenseId));
    }

    @Test
    void addLicenseWithMissingLicenseThrows() {
        ContentRepository contentRepository = Mockito.mock(ContentRepository.class);
        LicenseRepository licenseRepository = Mockito.mock(LicenseRepository.class);
        ContentValidation validation = Mockito.mock(ContentValidation.class);
        ContentServiceImp service = new ContentServiceImp(contentRepository, validation, licenseRepository);

        Long contentId = 3L;
        Long licenseId = 4L;

        Mockito.when(contentRepository.findById(contentId)).thenReturn(Optional.of(new com.cmsApp.cms.model.Content()));
        Mockito.when(licenseRepository.findById(licenseId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> service.addLicenseToContent(contentId, licenseId));
    }

    @Test
    void addLicenseWithExistingIdsSucceeds() throws TimeWindowException {
        ContentRepository contentRepository = Mockito.mock(ContentRepository.class);
        LicenseRepository licenseRepository = Mockito.mock(LicenseRepository.class);
        ContentValidation validation = Mockito.mock(ContentValidation.class);
        ContentServiceImp service = new ContentServiceImp(contentRepository, validation, licenseRepository);

        Long contentId = 5L;
        Long licenseId = 6L;

        Content content = new Content();
        License license = new License();

        Mockito.when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));
        Mockito.when(licenseRepository.findById(licenseId)).thenReturn(Optional.of(license));
        Mockito.when(contentRepository.save(content)).thenReturn(content);
        Mockito.when(validation.isLicenseValidForContent(content, license)).thenReturn(true);

        Content result = service.addLicenseToContent(contentId, licenseId);

        assertSame(content, result);
        assertTrue(content.getLicensesOfContent().contains(license));
    }
}
