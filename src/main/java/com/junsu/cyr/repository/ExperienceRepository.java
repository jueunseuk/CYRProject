package com.junsu.cyr.repository;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
}
