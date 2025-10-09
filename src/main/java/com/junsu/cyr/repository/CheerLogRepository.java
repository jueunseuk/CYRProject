package com.junsu.cyr.repository;

import com.junsu.cyr.domain.cheers.CheerLog;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheerLogRepository extends JpaRepository<CheerLog, Long> {
    Optional<CheerLog> findCheerLogByUser(User user);

    @Query("select count(cl) from CheerLog cl")
    Long countCheers();
}
