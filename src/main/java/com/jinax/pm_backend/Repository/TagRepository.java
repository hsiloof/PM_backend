package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    public Optional<Tag> getTagByNameEquals(String name);
}
