package com.junsu.cyr.repository;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.temperature.TemperatureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, Long> {
}
