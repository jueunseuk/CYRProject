package com.junsu.cyr.repository;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.sand.Sand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SandRepository extends JpaRepository<Sand, Integer> {
}
