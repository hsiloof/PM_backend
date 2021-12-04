package com.jinax.pm_backend.Service;

import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Repository.TagRepository;
import com.jinax.pm_backend.Result.GetTopTagResult;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);
    private final TagRepository tagRepository;
    private final EntityManager entityManager;

    public TagService(TagRepository tagRepository, EntityManager entityManager) {
        this.tagRepository = tagRepository;
        this.entityManager = entityManager;
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
    public List<Tag> getTagsByKeyword(String keyword){
        return tagRepository.getTagsByNameLike("%"+keyword+"%");
    }

    public List<GetTopTagResult> getTopTags() {
        Query nativeQuery = entityManager.createNativeQuery("select name,tic.id as id,tic.count as count from tag left join (select tag_id as id,count(*) as count from post_tag_relation group by tag_id) as tic on tag.id = tic.id order by tic.count limit 20");
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(GetTopTagResult.class));
        List<GetTopTagResult> topTags = nativeQuery.getResultList();
        return topTags;
    }
}
