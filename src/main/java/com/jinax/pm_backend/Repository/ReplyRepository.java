package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    Page<Reply> getAllByIsDeleted(short isDeleted, Pageable pageable);
    @Modifying
    @Query("update Reply m set m.isDeleted=?2 where  m.id=?1")
    @Transactional
    void updateReply(Integer id, short isDeleted);
}
