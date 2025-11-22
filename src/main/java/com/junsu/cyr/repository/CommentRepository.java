package com.junsu.cyr.repository;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    @Query("select count(c) from Comment c where c.user = :user")
    Long countByUser(User user);

    Page<Comment> findAllByUser(User user, Pageable pageable);

    Page<Comment> findAllByUserAndLocked(User user, Boolean locked, Pageable pageable);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime now);

    Page<Comment> findAllByContentContaining(String keyword, Pageable pageable);

    List<Comment> findByPostAndFixed(Post post, Boolean fixed);

    List<Comment> findByPostOrderByCreatedAtDesc(Post post);

    Long countByPostAndFixed(Post post, Boolean aTrue);
}
