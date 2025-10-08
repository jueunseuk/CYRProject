package com.junsu.cyr.repository;

import com.junsu.cyr.domain.empathys.Empathy;
import com.junsu.cyr.domain.empathys.EmpathyId;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpathyRepository extends JpaRepository<Empathy, EmpathyId> {
    @Query("select count(e) from Empathy e where e.user = :user")
    Long countByUser(User user);

    @Query("select e from Empathy e where e.user.userId = :userId")
    Page<Empathy> findAllByUser(Integer userId, Pageable pageable);
}
