package com.junsu.cyr.model.calendar;

import com.junsu.cyr.domain.calendar.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarUploadRequest {
    private String title;
    private String description;
    private String location;
    private String date;
    private String link1;
    private String link2;
    private Type type;
    private MultipartFile file;
}
