package com.junsu.cyr.service.announcement;

import com.junsu.cyr.domain.announcements.AnnouncementCategory;
import com.junsu.cyr.repository.AnnouncementCategoryRepository;
import com.junsu.cyr.response.exception.code.AnnouncementExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementCategoryService {

    private final AnnouncementCategoryRepository announcementCategoryRepository;

    public AnnouncementCategory getAnnouncementCategoryByAnnouncementCategoryId(Integer announcementCategoryId) {
        return announcementCategoryRepository.findById(announcementCategoryId)
                .orElseThrow(() -> new BaseException(AnnouncementExceptionCode.NOT_FOUND_ANNOUNCEMENT_CATEGORY));
    }
}
