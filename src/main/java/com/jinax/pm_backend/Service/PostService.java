package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Exception.InvalidPostException;
import com.jinax.pm_backend.Repository.PostRepository;
import com.jinax.pm_backend.param.SearchAddressRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(int id) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.orElse(null);
        LOGGER.info("getPostById, id : {}, post : {}", id, post);
        return post;
    }

    public Post getPostByIdNotDeleted(int id) {
        Optional<Post> byId = postRepository.getPostByIdAndIsDeletedEquals(id, (short) 0);
        Post post = byId.orElse(null);
        LOGGER.info("getPostByIdNotDeleted, id : {}, post : {}", id, post);
        return post;
    }

    public List<Post> getPostsByOwnerId(int ownerId) {
        List<Post> byPoster = postRepository.getPostsByOwnerId(ownerId);
//        LOGGER.info("getPostByPoster, poster : {}, post : {}",id,post);
        return byPoster;
    }

    //    public List<Post> getPostsByAddress(String address){
//        List<Post> byAddress = postRepository.getPostsByAddress(address);
//        return byAddress;
//    }
    public List<Post> getPostsByViewTimeGreaterThanEqual(int viewTime) {
        List<Post> byViews = postRepository.getPostsByViewTimeGreaterThanEqual(viewTime);
        return byViews;
    }

    public List<Post> getPostsByReplierId(int replierId) {
        List<Post> postsByReplierIdEquals = postRepository.getPostsByReplierIdEquals(replierId);
        return postsByReplierIdEquals;
    }

    public Post creatPost(Post post) throws InvalidPostException {
        post.setIsDeleted((short) 0);
        post.setCreateTime(new Date());
        post.setViewTime(0);
        Post savePost = postRepository.save(post);
        LOGGER.info("createPost, post before create : {}, post after create : {}", post, savePost);
        return savePost;
    }

    public List<Post> getPostsByContent(String keyword, Integer page, Integer size) {
        Page<Post> dataList = postRepository.getPostsByContent(keyword, keyword, PageRequest.of(page, size));
        return dataList.get().collect(Collectors.toList());
    }


    public List<Post> getPostsByAuthor(String author, Integer page, Integer size) {
        return postRepository.getPostsByOwnerNameLike(author, PageRequest.of(page, size))
                .get()
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByLocation(Double longitude, Double latitude, Double radius, Integer page, Integer size) {
        return postRepository.findAll().stream()
                .filter(item -> isInCircle(item, latitude, longitude, radius))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    private boolean isInCircle(Post post, Double latitude, Double longitude, Double radius) {
        return (Math.pow(post.getLatitude() - latitude, 2) + Math.pow(post.getLongitude() - longitude, 2) < Math.pow(radius, 2));
    }

    public List<Post> getPostsByAddress(SearchAddressRequest request) {
        Specification<Post> postSpecification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (request.getCity() != null&&!request.getCity().isEmpty()) {
                    predicates.add(cb.like(root.get("city"), "%"+request.getCity()+"%"));
                }
                if (request.getDistrict() != null&&!request.getDistrict().isEmpty()) {
                    predicates.add(cb.like(root.get("district"), "%"+request.getDistrict()+"%"));
                }
                if (request.getProvince() != null&&!request.getProvince().isEmpty()) {
                    predicates.add(cb.like(root.get("province"), "%"+request.getProvince()+"%"));
                }
                if (request.getStreet() != null&&!request.getStreet().isEmpty()) {
                    predicates.add(cb.like(root.get("street"), "%"+request.getStreet()+"%"));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        return postRepository.findAll(postSpecification, PageRequest.of(request.getPage(), request.getSize()))
                .get()
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByTags(List<String> tags, Integer page, Integer size) {
        return postRepository.findAll().stream()
                .filter(item -> isTagsAllInPost(item, tags))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * 帖子中是否包含所有tag
     */
    private boolean isTagsAllInPost(Post post, List<String> tags) {
        return post.getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
                .containsAll(tags);
    }
}
