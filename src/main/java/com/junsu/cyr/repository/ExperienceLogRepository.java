package com.junsu.cyr.repository;

import com.junsu.cyr.domain.experiences.ExperienceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExperienceLogRepository extends JpaRepository<ExperienceLog, Long> {
    @Query("select el from ExperienceLog as el where el.createdAt between :start and :end")
    List<ExperienceLog> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("select el from ExperienceLog as el where el.user.userId = :userId and el.createdAt between :start and :end")
    List<ExperienceLog> findAllByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("select el from ExperienceLog as el where el.experience.experienceId = :experienceId and el.createdAt between :start and :end")
    List<ExperienceLog> findAllByExperienceIdAndCreatedAtBetween(Integer experienceId, LocalDateTime start, LocalDateTime end);

    @Query("select el from ExperienceLog as el where el.user.userId = :userId and el.experience.experienceId = :experienceId and el.createdAt between :start and :end")
    List<ExperienceLog> findAllByUserIdAndExperienceIdAndCreatedAtBetween(Integer userId, Integer experienceId, LocalDateTime start, LocalDateTime end);
}
