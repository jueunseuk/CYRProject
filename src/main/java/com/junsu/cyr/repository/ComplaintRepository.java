package com.junsu.cyr.repository;

import com.junsu.cyr.domain.complaints.Complaint;
import com.junsu.cyr.domain.complaints.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Page<Complaint> findAllByStatus(Status status, Pageable pageable);
}
