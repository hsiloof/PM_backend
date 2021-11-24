package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Content;
import com.jinax.pm_backend.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content,Integer> {

}
