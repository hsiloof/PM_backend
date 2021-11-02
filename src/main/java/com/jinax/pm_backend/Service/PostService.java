package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.Post;
import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Exception.InvalidPostException;
import com.jinax.pm_backend.Repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post getPostById(int id){
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.orElse(null);
        LOGGER.info("getPostById, id : {}, post : {}",id,post);
        return post;
    }
    public List<Post> getPostsByOwnerId(int ownerId){
        List<Post> byPoster = postRepository.getPostsByOwnerId(ownerId);
//        LOGGER.info("getPostByPoster, poster : {}, post : {}",id,post);
        return byPoster;
    }
    public List<Post> getPostsByAddress(String address){
        List<Post> byAddress = postRepository.getPostsByAddress(address);
        return byAddress;
    }
    public List<Post> getPostsByViewTimeGreaterThanEqual(int viewTime){
        List<Post> byViews = postRepository.getPostsByViewTimeGreaterThanEqual(viewTime);
        return byViews;
    }
    public Post creatPost(Post post) throws InvalidPostException{
        post.setIsDeleted((short) 0);
        post.setCreateTime(new Date());
        Post savePost = postRepository.save(post);
        LOGGER.info("createPost, post before create : {}, post after create : {}",post,savePost);
        return savePost;
    }

}
