package com.junsu.cyr.repository;

import com.junsu.cyr.domain.gallery.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByOrderByNameAsc();
    boolean existsByName(String name);
    Tag findByName(String name);
}
