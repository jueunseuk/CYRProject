package com.junsu.cyr.repository;

import com.junsu.cyr.domain.temperature.TemperatureLog;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, Long> {
    @Query("select tl from TemperatureLog as tl where tl.createdAt between :start and :end")
    List<TemperatureLog> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("select tl from TemperatureLog as tl where tl.user.userId = :userId and tl.createdAt between :start and :end")
    List<TemperatureLog> findAllByUserIdAndCreatedAtBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    @Query("select tl from TemperatureLog as tl where tl.temperature.temperatureId = :temperatureId and tl.createdAt between :start and :end")
    List<TemperatureLog> findAllByTemperatureIdAndCreatedAtBetween(Integer temperatureId, LocalDateTime start, LocalDateTime end);

    @Query("select tl from TemperatureLog as tl where tl.user.userId = :userId and tl.temperature.temperatureId = :temperatureId and tl.createdAt between :start and :end")
    List<TemperatureLog> findAllByUserIdAndTemperatureIdAndCreatedAtBetween(Integer userId, Integer temperatureId, LocalDateTime start, LocalDateTime end);

    @Query("select function('date', tl.createdAt) as date, max(tl.after) as after from TemperatureLog as tl where tl.user.userId = :userId and tl.createdAt between :start and :end group by function('date', tl.createdAt)")
    List<DailyMaxProjection> findDailyMaxByUserId(Integer userId, LocalDateTime start, LocalDateTime end);
}
