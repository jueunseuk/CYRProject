package com.junsu.cyr.model.announcement;

import com.junsu.cyr.domain.announcements.Locked;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementUploadRequest {
    private String title;
    private String content;
    private Boolean fix = Boolean.FALSE;
    private Locked locked = Locked.PUBLIC;
    private Integer announcementCategoryId;
}
