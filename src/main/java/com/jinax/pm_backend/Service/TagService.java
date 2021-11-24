package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public Set<Tag> createTags(Set<Tag> tags){
        Set<Tag> tagSet = new HashSet<>();
        for (Tag tag:tags){
            Optional<Tag> byName=tagRepository.getTagByNameEquals(tag.getName());
            Tag createdTag = byName.orElse(null);
            if (createdTag==null){
                createdTag = tagRepository.save(tag);
            }
            tagSet.add(createdTag);
//            try {
//
//            }catch (Exception e){
//                System.out.println(tag);
//
//                LOGGER.info(tag.getName()+ "is existed");
//            }finally {
//                tagSet.add(createdTag);
//            }
        }
        return tagSet;
    }
}
