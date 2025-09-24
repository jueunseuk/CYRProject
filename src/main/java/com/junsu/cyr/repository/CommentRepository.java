package com.junsu.cyr.repository;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
