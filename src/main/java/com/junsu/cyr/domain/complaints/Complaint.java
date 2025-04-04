package com.junsu.cyr.domain.complaints;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "complaint")
public class Complaint extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id", nullable = false, updatable = false)
    private Long complaint_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_category")
    private ComplaintCategory complaintCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user")
    private User reporterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processor_user")
    private User processorUser;

    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;

    @Column(name = "capture_url")
    private String captureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
