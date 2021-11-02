package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}
