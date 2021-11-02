package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    public Tag getTagById(int id){
        Optional<Tag> byId = tagRepository.findById(id);
        Tag tag = byId.orElse(null);
        LOGGER.info("getTagById, id : {}, tag : {}",id,tag);
        return tag;
    }
}
