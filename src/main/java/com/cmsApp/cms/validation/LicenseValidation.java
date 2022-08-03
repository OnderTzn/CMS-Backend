package com.cmsApp.cms.validation;


import com.cmsApp.cms.model.License;
import com.cmsApp.cms.repository.LicenseRepository;
import org.springframework.stereotype.Component;

@Component
public class LicenseValidation {

    LicenseRepository licenseRepository;

    public boolean isLicenseTimeValid(Long startTime, Long endTime) {
        return startTime < endTime;     //if startTime smaller, return true
    }

    public boolean isLicenseExist(License license){
        for(License i: licenseRepository.findAll()){
            if(i.equals(license)){
                return true;
            }
        }
        return false;
    }

}
