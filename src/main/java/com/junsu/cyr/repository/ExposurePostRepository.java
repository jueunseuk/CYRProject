package com.junsu.cyr.repository;

import com.junsu.cyr.domain.posts.ExposurePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExposurePostRepository extends JpaRepository<ExposurePost, Long> {
    void deleteByCreatedAtBefore(LocalDateTime before);
}
