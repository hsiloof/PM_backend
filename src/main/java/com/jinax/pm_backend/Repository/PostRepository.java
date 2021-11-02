package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> getPostsByOwnerId(int ownerId);
    List<Post> getPostsByAddress(String address);
    List<Post> getPostsByViewTimeGreaterThanEqual(int viewTime);
}
