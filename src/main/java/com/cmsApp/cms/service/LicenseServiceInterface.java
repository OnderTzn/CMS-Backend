package com.cmsApp.cms.service;

import com.cmsApp.cms.model.License;

import java.util.List;

public interface LicenseServiceInterface {

    void deleteLicense(Long id);

    List<License> findAllLicenses();
}
