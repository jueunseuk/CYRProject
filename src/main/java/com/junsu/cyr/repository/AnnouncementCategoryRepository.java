package com.junsu.cyr.repository;

import com.junsu.cyr.domain.announcements.AnnouncementCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementCategoryRepository extends JpaRepository<AnnouncementCategory, Integer> {
}
