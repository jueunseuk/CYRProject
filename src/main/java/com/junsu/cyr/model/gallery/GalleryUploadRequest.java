package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.images.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class GalleryUploadRequest {
    private String title;
    private String description;
    private Type type;
    private String picturedAt;
    private List<String> tag;
    private List<String> originalImages;
    private List<MultipartFile> images;
}
