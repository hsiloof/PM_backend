package com.jinax.pm_backend.Repository;

import com.jinax.pm_backend.Entity.Tag;
import com.jinax.pm_backend.Result.GetTopTagResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    public Optional<Tag> getTagByNameEquals(String name);
    public List<Tag> getTagsByNameLike(String name);
}
