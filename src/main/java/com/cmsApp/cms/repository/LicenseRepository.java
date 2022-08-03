package com.cmsApp.cms.repository;

import com.cmsApp.cms.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {

    void deleteLicenseById(Long id);

    License findLicenseById(Long licenseId);    //Optional<License> findLicenseById(Long licenseId)


}
