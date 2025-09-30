package com.junsu.cyr.repository;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.experiences.ExperienceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceLogRepository extends JpaRepository<ExperienceLog, Long> {
}
