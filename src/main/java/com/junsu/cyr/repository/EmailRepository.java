package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    Optional<Email> findByEmail(String email);
}
