package com.junsu.cyr.repository;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    Optional<Achievement> findByTypeAndConditionAmount(Type type, Integer conditionAmount);
}
