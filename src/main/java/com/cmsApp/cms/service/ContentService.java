package com.cmsApp.cms.service;

import com.cmsApp.cms.exception.ItemNotFoundException;
import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.model.Status;
import com.cmsApp.cms.repository.ContentRepository;
import com.cmsApp.cms.validation.ContentValidation;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ContentService implements ContentServiceInterface {

    private final ContentRepository contentRepository;
    private final ContentValidation contentValidation;

    //Add Content
    public Content addContent(Content content){
        content.setContentCode(UUID.randomUUID().toString());
        return contentRepository.save(content);
    }

    //Add License to Content
    public Content addLicenseToContent(Content content, License license) throws TimeWindowException {

        if(contentValidation.isLicenseValidForContent(content, license)){
            content.addLicenseToContent(license);
            return contentRepository.save(content);
        }
        else
            throw new TimeWindowException("Time windows is overlapped.");
    }


    //Delete Content
    public void deleteContent(Long id){
        contentRepository.deleteById(id);
    }

    //Delete License from Content
    public void deleteLicenseFromContent(Content content, License license) {
        if (contentRepository.isContentExist(content.getId())) {
            if (contentValidation.isLicenseInList(content, license)){
            content.deleteLicenseFromContent(license);

            }
            else {
                throw new ItemNotFoundException("There is no license in the list with this id.");
            }
        }
        else
            throw new ItemNotFoundException("There is no content with this id");
    }


    //Update Content
    public void updateContent(Long contentId, Content content){
        for (Content c: contentRepository.findAll()){
            if(contentId.equals(c.getId())){
                c.updateContent(content);
                return;
            }
        }
        throw new ItemNotFoundException("Content not found with this id.");
    }


    @Scheduled(fixedRate = 5000L)
    public void updateStatus(){

        for (Content c: contentRepository.findAll()){   //contentList
            boolean shouldPublished = false;
            for (License l: c.getLicensesOfContent()){
                Long startTime = l.getStartTime();
                Long endTime = l.getEndTime();

                //The content's status should be Published, if true
                if (contentValidation.isTimeAvailableToPublished(startTime, endTime)){  //(startTime < localTime) && (endTime > localTime)
                    //Change status of the content
                    c.setStatus(Status.Published);
                    contentRepository.save(c);
                    shouldPublished = true;
                    break;
                }
            }
            //The content's status should be InProgress
            //if it should be Published, this statement will not run
            if(!shouldPublished) {
                c.setStatus(Status.InProgress);
                contentRepository.save(c);
            }
        }
    }



    public Content findContentById(Long id){
        return contentRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("Content by id:" + id + " was not found"));
    }


    public List<Content> findAllContents(){
        return contentRepository.findAll();
    }


}