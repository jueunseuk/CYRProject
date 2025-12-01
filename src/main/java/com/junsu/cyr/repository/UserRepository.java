package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Integer userId);
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime now);

    @Query("select u from User u where u.userId != :userId and u.status != :status")
    List<User> findAllByUserId(Integer userId, Status status, Pageable pageable);

    List<User> findAllByRole(Role role, Pageable pageable);

    Page<User> findAllByNicknameContaining(String keyword, Pageable pageable);

    List<User> findTop10ByOrderByConsecutiveAttendanceCntDesc();

    List<User> findTop10ByOrderByEpxCntDesc();

    List<User> findAllByStatusAndDeletedAtBefore(Status status, LocalDateTime aMonthAgo);

    List<User> findAllByStatus(Status status);
}
