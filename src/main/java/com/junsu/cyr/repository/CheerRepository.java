package com.junsu.cyr.repository;

import com.junsu.cyr.domain.cheers.Cheer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheerRepository extends JpaRepository<Cheer, Integer> {
    Optional<Cheer> findCheerByUser_UserId(Integer cheerId);
    @Query("select sum(c.sum) from Cheer c")
    Long sumCheer();
}
