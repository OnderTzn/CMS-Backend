package com.cmsApp.cms.service;

import com.cmsApp.cms.model.Content;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentServiceInterface {
    void deleteContent(Long id);

    List<Content> findAllContents();

}
