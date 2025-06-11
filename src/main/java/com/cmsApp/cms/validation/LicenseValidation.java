package com.cmsApp.cms.validation;

import com.cmsApp.cms.model.License;
import org.springframework.stereotype.Component;

@Component
public class LicenseValidation {

    public boolean isLicenseTimeValid(Long startTime, Long endTime) {
        return startTime < endTime;     //if startTime smaller, return true
    }
}