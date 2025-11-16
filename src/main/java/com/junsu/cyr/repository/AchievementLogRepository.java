package com.junsu.cyr.repository;

import com.junsu.cyr.domain.achievements.AchievementLog;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementLogRepository extends JpaRepository<AchievementLog, Long> {
    List<AchievementLog> findAllByUser(User user, Pageable pageable);
}
