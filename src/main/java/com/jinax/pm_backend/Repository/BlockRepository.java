package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Block;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlockRepository extends PagingAndSortingRepository<Block,Integer> {
    List<Block> getBlocksByPostId(int postId);
    Page<Block> findAllByPostIdEquals(int id, Pageable pageable);
    List<Block> getBlocksByOwnerId(int ownerId);
    Page<Block> getBlockByIsDeleted(short isDeleted,Pageable pageable);
    @Modifying
    @Query("update Block m set m.isDeleted=?2 where  m.id=?1")
    @Transactional
    void updateBlock(Integer id, short isDeleted);
}
