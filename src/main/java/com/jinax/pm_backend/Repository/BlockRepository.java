package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Block;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BlockRepository extends PagingAndSortingRepository<Block,Integer> {
    List<Block> getBlocksByPostId(int postId);
    List<Block> getBlocksByOwnerId(int ownerId);
}
