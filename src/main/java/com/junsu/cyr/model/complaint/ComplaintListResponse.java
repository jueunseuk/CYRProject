package com.junsu.cyr.model.complaint;

import com.junsu.cyr.domain.complaints.Complaint;
import com.junsu.cyr.domain.complaints.Status;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintListResponse {
    private Long complaintId;
    private Integer userId;
    private String nickname;
    private String title;
    private Status status;
    private String categoryName;
    private String categoryKorean;
    private LocalDateTime createdAt;

    public ComplaintListResponse(Complaint complaint) {
        this.complaintId = complaint.getComplaintId();
        this.title = complaint.getTitle();
        this.status = complaint.getStatus();
        this.createdAt = complaint.getCreatedAt();
        User user = complaint.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.categoryName = complaint.getComplaintCategory().getName();
        this.categoryKorean = complaint.getComplaintCategory().getKorean();
    }
}
