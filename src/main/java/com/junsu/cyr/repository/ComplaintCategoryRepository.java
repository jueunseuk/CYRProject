package com.junsu.cyr.repository;

import com.junsu.cyr.domain.complaints.ComplaintCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Integer> {
    ComplaintCategory findByName(String name);
}
