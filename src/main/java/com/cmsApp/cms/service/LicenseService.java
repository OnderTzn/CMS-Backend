package com.cmsApp.cms.service;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.License;

import java.util.List;

public interface LicenseService {

    void addLicense(License license) throws TimeWindowException;
    License updateLicense(License license);
    void deleteLicense(Long licenseId);
    License findLicenseById(Long licenseId);
    List<License> findAllLicenses();
}
