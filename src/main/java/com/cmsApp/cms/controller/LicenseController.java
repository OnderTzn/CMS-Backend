package com.cmsApp.cms.controller;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license")
@CrossOrigin
public class LicenseController{

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<License>> getAllLicenses(){
        List<License> licenses = licenseService.findAllLicenses();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<License> getLicenseById (@PathVariable("id") Long id) {
        License license = licenseService.findLicenseById(id);
        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<License> addLicense(@RequestBody License license) throws TimeWindowException {
        licenseService.addLicense(license);
        return new ResponseEntity<>(license, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<License> updateLicense(@RequestBody License license){
        License updateLicense = licenseService.updateLicense(license);
        return new ResponseEntity<>(updateLicense, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{licenseId}")
    public ResponseEntity<?> deleteLicense(@PathVariable("licenseId") Long licenseId){
        licenseService.deleteLicense(licenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
