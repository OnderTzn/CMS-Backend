package com.cmsApp.cms.validation;

import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ContentValidation {

    public boolean isLicenseValidForContent(Content content, License license) {
        //If the license is already added
        if (content.getLicensesOfContent().contains(license)){
            throw new IllegalArgumentException("The content already has the license.");
        }
        //License is not added, yet
        else {
            //Controls if the license to be added conflicts with other licences.
            for(License l: content.getLicensesOfContent()){
                //Conflict in startTime
                if((license.getStartTime() < l.getStartTime())
                        && (license.getEndTime() > l.getStartTime())) {
                    return false;
                }
                //Conflict in the middle
                else if((license.getStartTime() > l.getStartTime())
                        && (license.getEndTime() < l.getEndTime())) {
                    return false;
                }
                //Conflict in the endTime
                else if((license.getStartTime() < l.getStartTime())
                        && (license.getEndTime() > l.getEndTime())) {
                    return false;
                }
                //Conflict in the startTime and endTime
                else if(license.getStartTime().equals(l.getStartTime())     //Conflict if startTime
                        || license.getEndTime().equals(l.getEndTime())) {    //OR endTime is equal
                    return false;
                }
                //No conflict with other licenses' timeframe
                else {
                    return true;
                }
            }
            //Content's license list is empty
            return true;
        }
    }

    public boolean isLicenseInList(Content content, License license){
        //if license is in the list, return true
        return content.getLicensesOfContent().contains(license);
    }

    public boolean isTimeAvailableToPublished(Long startTime, Long endTime) {
        /* ---> getLocalTime()
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Long localTime = zonedDateTime.toInstant().toEpochMilli();
        */

        Long localTime = getLocalTime();
        //If current time is in the license's time frame, return true
        return (startTime < localTime) && (endTime > localTime);
    }

    public Long getLocalTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zonedDateTime.toInstant().toEpochMilli();
    }
}
