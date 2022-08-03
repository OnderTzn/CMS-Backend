package com.cmsApp.cms.repository;

import com.cmsApp.cms.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface LicenseRepository extends JpaRepository<License, Long> {

    //void deleteLicenseById(Long id);

    License findLicenseById(Long licenseId);    //Optional<License> findLicenseById(Long licenseId)
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM content_licenses_of_content WHERE license_id = :licenseId", nativeQuery = true)
    void deleteByLicenseId(@Param("licenseId") Long licenseId);
}
