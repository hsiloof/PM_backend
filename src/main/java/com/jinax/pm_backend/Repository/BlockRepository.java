package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Block;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BlockRepository extends PagingAndSortingRepository<Block,Integer> {
    List<Block> getBlocksByPostId(int postId);
    Page<Block> findAllByPostIdEquals(int id, Pageable pageable);
    List<Block> getBlocksByOwnerId(int ownerId);
}
