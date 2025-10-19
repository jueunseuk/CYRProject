package com.junsu.cyr.model.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRequest {
    private String categoryName;
    private String title;
    private String reason;
    private MultipartFile file;
}
