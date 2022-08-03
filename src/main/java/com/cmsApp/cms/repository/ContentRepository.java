package com.cmsApp.cms.repository;

import com.cmsApp.cms.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findContentById(Long contentId);

    default boolean isContentExist(Long contentId){
        //if content exist, return true
        return findContentById(contentId) != null;
    }
}


























