package com.junsu.cyr.model.complaint;

import com.junsu.cyr.domain.complaints.Complaint;
import com.junsu.cyr.domain.complaints.Status;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintResponse {
    private Long complaintId;
    private Integer userId;
    private String nickname;
    private String title;
    private String reason;
    private String captureUrl;
    private Status status;
    private LocalDateTime createdAt;
    private Integer processorId;
    private String processorNickname;
    private LocalDateTime processedAt;
    private String processedMessage;
    private String categoryName;

    public ComplaintResponse(Complaint complaint) {
        this.complaintId = complaint.getComplaintId();
        this.title = complaint.getTitle();
        this.reason = complaint.getReason();
        this.captureUrl = complaint.getCaptureUrl();
        this.status = complaint.getStatus();
        this.createdAt = complaint.getCreatedAt();
        User user = complaint.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        if(complaint.getProcessorUser() != null) {
            User processor = complaint.getProcessorUser();
            this.processorId = processor.getUserId();
            this.processorNickname = processor.getNickname();
        }
        this.processedAt = complaint.getProcessedAt();
        this.processedMessage = complaint.getProcessMessage();
        this.categoryName = complaint.getComplaintCategory().getName();
    }
}
