package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Content;
import com.jinax.pm_backend.Repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class ContentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentService.class);
    private final ContentRepository contentRepository;
    public ContentService(ContentRepository contentRepository){
        this.contentRepository = contentRepository;
    }
    public Content createContent(Content content){
        Content saveContent = contentRepository.save(content);
        LOGGER.info("createContent, content before create: {}, content after create: {}",content,saveContent);
        return saveContent;
    }

}
