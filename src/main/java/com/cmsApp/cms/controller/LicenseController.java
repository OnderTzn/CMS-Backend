package com.cmsApp.cms.controller;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.service.LicenseServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license")
@CrossOrigin
@AllArgsConstructor
public class LicenseController {

    private final LicenseServiceImp licenseServiceImp;

    //Get all the license
    @GetMapping("/all")
    public ResponseEntity<List<License>> getAllLicenses() {
        List<License> licenses = licenseServiceImp.findAllLicenses();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    //Get license by id
    @GetMapping("/find/{id}")
    public ResponseEntity<License> getLicenseById(@PathVariable("id") Long id) {
        License license = licenseServiceImp.findLicenseById(id);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    //Add new license
    @PostMapping("/add")
    public ResponseEntity<License> addLicense(@RequestBody License license) throws TimeWindowException {
        licenseServiceImp.addLicense(license);
        return new ResponseEntity<>(license, HttpStatus.CREATED);
    }

    //Update a license
    @PutMapping("/update{licenseId}")
    public ResponseEntity<License> updateLicense(@PathVariable Long licenseId, @RequestBody License license) {
        licenseServiceImp.updateLicense(licenseId, license);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    //Delete a license
    @DeleteMapping("/delete/{licenseId}")
    public ResponseEntity<?> deleteLicense(@PathVariable("licenseId") Long licenseId) {
        licenseServiceImp.deleteLicense(licenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
