package com.junsu.cyr.repository;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.AchievementReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRewardRepository extends JpaRepository<AchievementReward, Long> {
    List<AchievementReward> findAllByAchievement(Achievement achievement);
}
