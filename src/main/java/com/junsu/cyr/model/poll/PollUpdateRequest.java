package com.junsu.cyr.model.poll;

import com.junsu.cyr.domain.polls.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollUpdateRequest {
    private String title;
    private String description;
    private LocalDateTime closedAt;
    private MultipartFile file;
    private Status status;
}
