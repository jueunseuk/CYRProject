package com.junsu.cyr.repository;

import com.junsu.cyr.domain.glass.GlassLog;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GlassLogRepository extends JpaRepository<GlassLog, Long> {
    @Query("select gl from GlassLog as gl where gl.createdAt between :start and :end")
    List<GlassLog> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("select gl from GlassLog as gl where gl.user.userId = :userId and gl.createdAt between :start and :end")
    List<GlassLog> findAllByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("select gl from GlassLog as gl where gl.glass.glassId = :sandId and gl.createdAt between :start and :end")
    List<GlassLog> findAllByGlassIdAndCreatedAtBetween(Integer sandId, LocalDateTime start, LocalDateTime end);

    @Query("select gl from GlassLog as gl where gl.user.userId = :userId and gl.glass.glassId = :sandId and gl.createdAt between :start and :end")
    List<GlassLog> findAllByUserIdAndGlassIdAndCreatedAtBetween(Integer userId, Integer sandId, LocalDateTime start, LocalDateTime end);

    @Query("select function('date', gl.createdAt) as date, max(gl.after) as after from GlassLog as gl where gl.user.userId = :userId and gl.createdAt between :start and :end group by function('date', gl.createdAt)")
    List<DailyMaxProjection> findDailyMaxByUserId(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("select gl from GlassLog as gl")
    List<GlassLog> findAllGlassLog(Pageable pageable);
}
