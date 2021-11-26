package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {
    Optional<Post> getPostByIdAndIsDeletedEquals(int id,short isDeleted);
    List<Post> getPostsByOwnerId(int ownerId);
    List<Post> getPostsByViewTimeGreaterThanEqual(int viewTime);
    @Query(value = "select distinct * from post where post.id in (select post_id from reply where owner_id=?)",nativeQuery = true)
    List<Post> getPostsByReplierIdEquals(int replierId);
    @Query(value = "select distinct * from post where post.content_id in(select id from content where content like %?1% ) or post.title like %?2% ",nativeQuery = true)
    Page<Post> getPostsByContent(String content,String title, Pageable pageable);
    @Query(value = "select * from post where post.owner_id in(select id from user where username like %?1%)",nativeQuery = true)
    Page<Post> getPostsByOwnerNameLike(String ownerName, Pageable pageable);
    List<Post> findAll();


}
