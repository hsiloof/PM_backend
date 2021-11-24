package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Optional<Post> getPostByIdAndIsDeletedEquals(int id,short isDeleted);
    List<Post> getPostsByOwnerId(int ownerId);
    List<Post> getPostsByViewTimeGreaterThanEqual(int viewTime);
}
