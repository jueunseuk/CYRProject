package com.junsu.cyr.repository;

import com.junsu.cyr.domain.glass.Glass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Integer> {
}
