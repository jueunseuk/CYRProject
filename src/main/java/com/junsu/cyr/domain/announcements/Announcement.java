package com.junsu.cyr.domain.announcements;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.announcement.AnnouncementUploadRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement")
public class Announcement extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_category", nullable = false)
    private AnnouncementCategory announcementCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "view_cnt")
    private Long viewCnt;

    @Column(name = "fix", nullable = false)
    private Boolean fix;

    @Enumerated(EnumType.STRING)
    @Column(name = "locked")
    private Locked locked;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(AnnouncementUploadRequest request) {
        this.title = request.getTitle() == null ? this.title : request.getTitle();
        this.content = request.getContent() == null ? this.content : request.getContent();
        this.fix = request.getFix() == null ? this.fix : request.getFix();
        this.locked = request.getLocked() == null ? this.locked : request.getLocked();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCategory(AnnouncementCategory announcementCategory) {
        this.announcementCategory = announcementCategory;
    }

    public void increaseViewCnt() {
        this.viewCnt++;
    }
}
