package com.junsu.cyr.repository;

import com.junsu.cyr.domain.sand.SandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SandLogRepository extends JpaRepository<SandLog, Long> {
}
