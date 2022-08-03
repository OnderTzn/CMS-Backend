package com.cmsApp.cms.service;

import com.cmsApp.cms.exception.TimeWindowException;
import com.cmsApp.cms.model.Content;
import com.cmsApp.cms.model.License;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentService {

    Content addContent(Content content);
    Content addLicenseToContent(Content content, License license) throws TimeWindowException;

    void deleteContent(Long id);
    void deleteLicenseFromContent(Content content, License license);

    void updateContent(Long contentId, Content content);
    void updateStatus();

    Content findContentById(Long id);
    List<Content> findAllContents();

}
