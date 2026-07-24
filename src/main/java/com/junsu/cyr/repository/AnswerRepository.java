package com.junsu.cyr.repository;

import com.junsu.cyr.domain.qnas.Answer;
import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("select a from Answer a where a.question = :question order by a.createdAt desc")
    List<Answer> findAllByQuestion(Question question);

    @Query("select a from Answer a where a.question = :question and a.adoptedAt is not null")
    boolean existsByAdoptAnswer(Question question);

    Long countAnswerByUser(User user);
}
