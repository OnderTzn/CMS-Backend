package com.cmsApp.cms.controller;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.service.LicenseServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license")
@CrossOrigin
public class LicenseController{

    private final LicenseServiceImp licenseServiceImp;

    public LicenseController(LicenseServiceImp licenseServiceImp) {
        this.licenseServiceImp = licenseServiceImp;
    }

    @GetMapping("/all")
    public ResponseEntity<List<License>> getAllLicenses(){
        List<License> licenses = licenseServiceImp.findAllLicenses();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<License> getLicenseById (@PathVariable("id") Long id) {
        License license = licenseServiceImp.findLicenseById(id);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<License> addLicense(@RequestBody License license) throws TimeWindowException {
        licenseServiceImp.addLicense(license);
        return new ResponseEntity<>(license, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<License> updateLicense(@RequestBody License license){
        License updateLicense = licenseServiceImp.updateLicense(license);
        return new ResponseEntity<>(updateLicense, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{licenseId}")
    public ResponseEntity<?> deleteLicense(@PathVariable("licenseId") Long licenseId){
        licenseServiceImp.deleteLicense(licenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
