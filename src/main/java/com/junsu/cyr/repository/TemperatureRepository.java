package com.junsu.cyr.repository;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.temperature.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
}
