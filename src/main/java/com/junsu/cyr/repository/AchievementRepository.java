package com.junsu.cyr.repository;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    Optional<Achievement> findByTypeAndScopeAndConditionAmount(Type type, Scope scope, Long conditionAmount);

    @Query("select a from Achievement a where a.type = :type and a.scope = :scope and a.conditionAmount <= :amount")
    List<Achievement> findAllBySatisfyCondition(Type type, Scope scope, Long amount);
}
