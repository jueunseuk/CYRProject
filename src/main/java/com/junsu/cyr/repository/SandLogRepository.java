package com.junsu.cyr.repository;

import com.junsu.cyr.domain.sand.SandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SandLogRepository extends JpaRepository<SandLog, Long> {
    @Query("select sl from SandLog as sl where sl.createdAt between :start and :end")
    List<SandLog> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("select sl from SandLog as sl where sl.user.userId = :userId and sl.createdAt between :start and :end")
    List<SandLog> findAllByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("select sl from SandLog as sl where sl.sand.sandId = :sandId and sl.createdAt between :start and :end")
    List<SandLog> findAllBySandIdAndCreatedAtBetween(Integer sandId, LocalDateTime start, LocalDateTime end);

    @Query("select sl from SandLog as sl where sl.user.userId = :userId and sl.sand.sandId = :sandId and sl.createdAt between :start and :end")
    List<SandLog> findAllByUserIdAndSandIdAndCreatedAtBetween(Integer userId, Integer sandId, LocalDateTime start, LocalDateTime end);
}
