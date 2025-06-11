package com.cmsApp.cms.service;

import com.cmsApp.cms.exception.ItemNotFoundException;
import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import com.cmsApp.cms.model.Status;
import com.cmsApp.cms.repository.ContentRepository;
import com.cmsApp.cms.repository.LicenseRepository;
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
public class ContentServiceImp implements ContentService {

    private final ContentRepository contentRepository;
    private final ContentValidation contentValidation;
    private final LicenseRepository licenseRepository;

    //Add Content
    public Content addContent(Content content) {
        content.setContentCode(UUID.randomUUID().toString());
        return contentRepository.save(content);
    }

    //Add License to Content
    public Content addLicenseToContent(Long contentId, Long licenseId) throws TimeWindowException {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ItemNotFoundException("Content not found with this id."));
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ItemNotFoundException("License not found with this id."));

        contentValidation.isLicenseValidForContent(content, license);
        content.addLicenseToContent(license);
        return contentRepository.save(content);
    }


    //Delete Content
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

    //Delete License from Content
    public void deleteLicenseFromContent(Long contentId, Long licenseId) {
        Content content = contentRepository.findContentById(contentId);
        License license = licenseRepository.findLicenseById(licenseId);

        if (contentRepository.isContentExist(contentId)) {
            if (contentValidation.isLicenseInList(content, license)) {
                content.deleteLicenseFromContent(license);
            } else {
                throw new ItemNotFoundException("There is no license in the list with this id.");
            }
        } else
            throw new ItemNotFoundException("There is no content with this id");
    }


    //Update Content
    public void updateContent(Long contentId, Content content) {
        for (Content c : contentRepository.findAll()) {
            if (contentId.equals(c.getId())) {
                c.updateContent(content);
                return;
            }
        }
        throw new ItemNotFoundException("Content not found with this id.");
    }


    //x000 means every x second
    @Scheduled(fixedRate = 15000L)   //Update status every 15 seconds
    public void updateStatus() {
        for (Content content : contentRepository.findAll()) {   //contentList
            boolean shouldPublished = false;
            assert content.getLicensesOfContent() != null;  //Is putting this here safe?
            for (License license : content.getLicensesOfContent()) {
                Long startTime = license.getStartTime();
                Long endTime = license.getEndTime();

                //The content's status should be Published, if true
                if (contentValidation.isTimeAvailableToPublish(startTime, endTime)) {
                    //Change status of the content
                    content.setStatus(Status.Published);
                    contentRepository.save(content);
                    shouldPublished = true;
                    break;
                }
            }
            //The content's status should be InProgress
            //if it should be Published, this statement will not run
            if (!shouldPublished) {
                content.setStatus(Status.InProgress);
                contentRepository.save(content);
            }
        }
    }

    //Find content by id
    public Content findContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Content by id:" + id + " was not found"));
    }

    //Find content by name
    public Content findContentByName(String contentName) {
        return contentRepository.findByName(contentName);
    }

    //Find all contents
    public List<Content> findAllContents() {
        return contentRepository.findAll();
    }


}
