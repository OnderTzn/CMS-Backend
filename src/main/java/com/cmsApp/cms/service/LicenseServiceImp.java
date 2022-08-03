package com.cmsApp.cms.service;

import com.cmsApp.cms.exception.ItemNotFoundException;
import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.repository.ContentRepository;
import com.cmsApp.cms.repository.LicenseRepository;
import com.cmsApp.cms.validation.LicenseValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class LicenseServiceImp implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final ContentRepository contentRepository;
    private final LicenseValidation licenseValidation;
    private final ContentServiceImp contentServiceImp;

    public void addLicense(License license) throws TimeWindowException {
        Long startTime = license.getStartTime();
        Long endTime = license.getEndTime();

        if (licenseValidation.isLicenseTimeValid(startTime, endTime)) {
            license.setLicenseCode(UUID.randomUUID().toString());
            licenseRepository.save(license);
        } else
            throw new TimeWindowException("Start time should be earlier than end time.");
    }

    public void updateLicense(Long licenseId, License license) {
        for (License existedLicense : licenseRepository.findAll()) {
            if (licenseId.equals(existedLicense.getId())) {
                existedLicense.updateLicense(license);
                return;
            }
        }
        throw new ItemNotFoundException("License not found with this id.");
    }

    //Delete license
    public void deleteLicense(Long licenseId) {
        licenseRepository.deleteByLicenseId(licenseId);
        //After deleting from contents' list, delete from repository
        licenseRepository.deleteById(licenseId);
    }

    public License findLicenseById(Long licenseId) {
        return licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ItemNotFoundException(("License by id:" + licenseId + " was not found")));
    }

    public List<License> findAllLicenses() {
        return licenseRepository.findAll();
    }
}