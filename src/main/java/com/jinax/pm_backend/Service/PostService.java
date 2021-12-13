package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Exception.InvalidPostException;
import com.jinax.pm_backend.Repository.PostRepository;
import com.jinax.pm_backend.param.SearchAddressRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        Optional<Post> byId = postRepository.getPostByIdAndIsDeletedLessThanEqual(id, (short) 1);
        Post post = byId.orElse(null);
        if(post != null){
            post.setViewTime(post.getViewTime() + 1);
            postRepository.save(post);
        }
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

    public Map<String, Object> getPostsByContent(String keyword, Integer page, Integer size) {
        List<Post> postList = postRepository.findAllByIsDeletedLessThanEqual((short) 1);
        long count = postList.stream()
                .filter(post -> post.getContent().getContent().contains(keyword) || post.getTitle().contains(keyword))
                .count();
        List<Post> data = postList.stream()
                .filter(post -> post.getContent().getContent().contains(keyword) || post.getTitle().contains(keyword))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
        return getStringObjectMap(page, (long) Math.ceil((double) count / size), data);
    }

    private Map<String, Object> getStringObjectMap(Page<Post> dataList) {
        Map<String, Object> map = new HashMap<>();
        List<Post> data = dataList
                .get()
                .collect(Collectors.toList());
        map.put("result", data);
        map.put("totalPage", dataList.getTotalPages());
        map.put("curPage", dataList.getNumber());
        return map;
    }


    public Map<String, Object> getPostsByAuthor(String author, Integer page, Integer size) {
        Page<Post> pageList = postRepository.getPostsByOwnerNameLike(author, PageRequest.of(page, size));
        return getStringObjectMap(pageList);
    }

    public Map<String, Object> getPostsByLocation(Double longitude, Double latitude, Double radius, Integer page, Integer size) {
        List<Post> dataList = postRepository.findAllByIsDeletedLessThanEqual((short) 1);
        long count = dataList.stream()
                .filter(item -> isInCircle(item, latitude, longitude, radius))
                .count();

        List<Post> data = dataList.stream()
                .filter(item -> isInCircle(item, latitude, longitude, radius))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
        return getStringObjectMap(page, (long) Math.ceil((double) count / size), data);
    }

    private boolean isInCircle(Post post, Double latitude, Double longitude, Double radius) {

        // 地球半径为R=6371.0 km
//        C = sin(LatA)*sin(LatB) + cos(LatA)*cos(LatB)*cos(MLonA-MLonB)
//
//Distance = R*Arccos(C)*Pi/180
        double r = 6371 * 1000;
//        return (Math.pow(post.getLatitude() - latitude, 2) + Math.pow(post.getLongitude() - longitude, 2) < Math.pow(radius, 2));
        double c = Math.sin(latitude) * Math.sin(post.getLatitude()) + Math.cos(latitude) * Math.cos(post.getLatitude()) * Math.cos(longitude - post.getLongitude());
        double distance = r * Math.acos(c) * Math.PI / 180;
        return distance <= radius;
    }

    public Map<String, Object> getPostsByAddress(SearchAddressRequest request) {
        Specification<Post> postSpecification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (request.getCity() != null && !request.getCity().isEmpty()) {
                    predicates.add(cb.like(root.get("city"), "%" + request.getCity() + "%"));
                }
                if (request.getDistrict() != null && !request.getDistrict().isEmpty()) {
                    predicates.add(cb.like(root.get("district"), "%" + request.getDistrict() + "%"));
                }
                if (request.getProvince() != null && !request.getProvince().isEmpty()) {
                    predicates.add(cb.like(root.get("province"), "%" + request.getProvince() + "%"));
                }
                if (request.getStreet() != null && !request.getStreet().isEmpty()) {
                    predicates.add(cb.like(root.get("street"), "%" + request.getStreet() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Post> dataList = postRepository.findAll(postSpecification, PageRequest.of(request.getPage(), request.getSize()));
        return getStringObjectMap(dataList);
    }

    @Transactional
    public Map<String, Object> getPostsByTags(String tagsStr, Integer page, Integer size) {
        String[] tags = tagsStr.split(",");
        List<String> tagList = Arrays.stream(tags).collect(Collectors.toList());
        List<Post> dataList = postRepository.findAllByIsDeletedLessThanEqual((short) 1);
        long count = dataList.stream()
                .filter(item -> isTagsAllInPost(item, tagList))
                .count();
        List<Post> collect = dataList.stream()
                .filter(item -> isTagsAllInPost(item, tagList))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
        return getStringObjectMap(page, (long) Math.ceil((double) count / size), collect);
    }

    private Map<String, Object> getStringObjectMap(Integer page, long totalPage, List<Post> collect) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", collect);
        map.put("totalPage", totalPage);
        map.put("curPage", page);
        return map;
    }
    @Cacheable(cacheNames = "topPosts")
    public List<Post> getTopPosts() {
        return postRepository.getTopPosts();
    }
    @CachePut(cacheNames = "topPosts")
    public List<Post> updateCacheTopPosts() {
        return postRepository.getTopPosts();
    }

    public List<Post> getTopPostsNearBy(double longitude, double latitude) {
        return postRepository.findAllByIsDeletedLessThanEqual((short) 1).stream()
                .filter(item -> isInCircle(item, latitude, longitude, (double) 20))
                .sorted(Comparator.comparing(Post::getViewTime).reversed())
                .limit(10)
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

    public Page<Post> getReportedPost(int page, int size) {
        return postRepository.getPostByIsDeleted((short) 1, PageRequest.of(page, size));
    }

    public void updateReportedPost(Integer id, short operation) {
        short state = (short) (operation == 0 ? 2 : 0);
        postRepository.updatePost(id, state);
    }

    public Post reportPost(int id) {
        Post post = getPostById(id);
        post.setIsDeleted((short)1);
        postRepository.save(post);
        return post;
    }

    public Post getRandomPost(){
        Optional<Post> randomPost = postRepository.getRandomPost();
        Post post = randomPost.orElse(null);
        LOGGER.info("getRandomPost,  post : {}", randomPost);
        return post;
    }
}
