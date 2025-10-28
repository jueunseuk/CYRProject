package com.junsu.cyr.repository;

import com.junsu.cyr.domain.applys.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Page<Apply> findAllBy(Pageable pageable);

    Page<Apply> findAllByConfirm(Boolean confirm, Pageable pageable);
}
