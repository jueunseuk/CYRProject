package com.junsu.cyr.repository;

import com.junsu.cyr.domain.posts.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByPostId(Long postId);

    @Query("SELECT p FROM Post p WHERE p.board.boardId BETWEEN :start AND :end")
    Page<Post> findAllNew(Integer start, Integer end, Pageable pageable);
}
