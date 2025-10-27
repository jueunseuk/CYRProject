package com.junsu.cyr.service.announcement;

import com.junsu.cyr.domain.announcements.Announcement;
import com.junsu.cyr.domain.announcements.AnnouncementCategory;
import com.junsu.cyr.domain.announcements.Locked;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.announcement.AnnouncementConditionRequest;
import com.junsu.cyr.model.announcement.AnnouncementResponse;
import com.junsu.cyr.model.announcement.AnnouncementUploadRequest;
import com.junsu.cyr.repository.AnnouncementRepository;
import com.junsu.cyr.response.exception.code.AnnouncementExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementCategoryService announcementCategoryService;
    private final UserService userService;

    public Announcement getAnnouncementByAnnouncementId(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BaseException(AnnouncementExceptionCode.NOT_FOUND_ANNOUNCEMENT));
    }

    public AnnouncementResponse getAnnouncement(Long announcementId, Integer userId) {
        userService.getUserById(userId);
        Announcement announcement = getAnnouncementByAnnouncementId(announcementId);

        return new AnnouncementResponse(announcement);
    }

    public Page<AnnouncementResponse> getAnnouncements(AnnouncementConditionRequest condition, Integer userId) {
        userService.getUserById(userId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getPageSize(), sort);
        Page<Announcement> announcements = announcementRepository.findAllByFixAndLocked(false, Locked.PUBLIC, pageable);

        return announcements.map(AnnouncementResponse::new);
    }

    public List<AnnouncementResponse> getAnnouncementsByFixed() {
        return announcementRepository.findAllByFixAndLockedOrderByCreatedAt(true, Locked.PUBLIC).stream().map(AnnouncementResponse::new).toList();
    }

    @Transactional
    public AnnouncementResponse uploadAnnouncement(AnnouncementUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        isValidUploadData(request);

        AnnouncementCategory announcementCategory =
                announcementCategoryService.getAnnouncementCategoryByAnnouncementCategoryId(request.getAnnouncementCategoryId());

        Announcement announcement = Announcement.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .fix(request.getFix())
                .locked(request.getLocked())
                .updatedAt(LocalDateTime.now())
                .announcementCategory(announcementCategory)
                .build();

        announcementRepository.save(announcement);

        return new AnnouncementResponse(announcement);
    }

    @Transactional
    public AnnouncementResponse updateAnnouncement(AnnouncementUploadRequest request, Long announcementId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        isValidUploadData(request);

        Announcement announcement = getAnnouncementByAnnouncementId(announcementId);
        announcement.update(request);

        AnnouncementCategory announcementCategory = announcementCategoryService.getAnnouncementCategoryByAnnouncementCategoryId(request.getAnnouncementCategoryId());
        announcement.updateCategory(announcementCategory);

        return new AnnouncementResponse(announcement);
    }

    @Transactional
    public void deleteAnnouncement(Long announcementId, Integer userId) {
        User user = userService.getUserById(userId);
        Announcement announcement = getAnnouncementByAnnouncementId(announcementId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        announcementRepository.delete(announcement);
    }

    public void isValidUploadData(AnnouncementUploadRequest request) {
        if(request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new BaseException(AnnouncementExceptionCode.TOO_SHORT);
        }
        if(request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new BaseException(AnnouncementExceptionCode.TOO_SHORT);
        }
        if(request.getFix() == null || request.getLocked() == null || request.getAnnouncementCategoryId() == null) {
            throw new BaseException(AnnouncementExceptionCode.CANNOT_BE_NULL);
        }
    }
}
