package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block,Integer> {
    List<Block> getBlocksByPostId(int postId);
    List<Block> getBlocksByOwnerId(int ownerId);
}
