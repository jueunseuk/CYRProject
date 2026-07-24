package com.junsu.cyr.repository;

import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.domain.qnas.Status;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllBy(Pageable of);

    List<Question> findAllByStatus(Status status, Pageable of);

    Long countQuestionByUser(User user);
}
