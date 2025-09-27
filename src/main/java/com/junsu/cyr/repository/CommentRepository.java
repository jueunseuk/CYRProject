package com.junsu.cyr.repository;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
