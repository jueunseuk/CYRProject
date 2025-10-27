package com.junsu.cyr.repository;

import com.junsu.cyr.domain.announcements.Announcement;
import com.junsu.cyr.domain.announcements.Locked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByFixAndLockedOrderByCreatedAt(boolean fix, Locked locked);

    Page<Announcement> findAllByFixAndLocked(boolean fix, Locked locked, Pageable pageable);
}
