package com.junsu.cyr.model.announcement;

import com.junsu.cyr.domain.announcements.Announcement;
import com.junsu.cyr.domain.announcements.AnnouncementCategory;
import com.junsu.cyr.domain.announcements.Locked;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementResponse {
    private Long announcementId;
    private String title;
    private String content;
    private Boolean fix;
    private Locked locked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private Integer announcementCategoryId;
    private String name;
    private String english;

    public AnnouncementResponse(Announcement announcement) {
        this.announcementId = announcement.getAnnouncementId();
        this.title = announcement.getTitle();
        this.content = announcement.getContent();
        this.fix = announcement.getFix();
        this.locked = announcement.getLocked();
        this.createdAt = announcement.getCreatedAt();
        this.updatedAt = announcement.getUpdatedAt();

        User user = announcement.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();

        AnnouncementCategory announcementCategory = announcement.getAnnouncementCategory();
        this.announcementCategoryId = announcementCategory.getAnnouncementCategoryId();
        this.name = announcementCategory.getName();
        this.english = announcementCategory.getEnglish();
    }
}
