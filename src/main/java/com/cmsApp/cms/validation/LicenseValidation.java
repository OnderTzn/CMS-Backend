package com.cmsApp.cms.validation;

import com.cmsApp.cms.model.License;
import org.springframework.stereotype.Component;

@Component
public class LicenseValidation {

    public boolean isLicenseTimeValid(Long startTime, Long endTime) {
        // returns true when startTime is earlier than endTime
        return startTime < endTime;
    }
}