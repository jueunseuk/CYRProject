package com.junsu.cyr.domain.images;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private Long size;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public static void validSize(Long size) {
        if(size > MAX_FILE_SIZE) {
            throw new BaseException(ImageExceptionCode.IMAGE_SIZE_EXCEEDED);
        }
    }

    @Value("${app.image-base-url}")
    private String imageBaseUrl;

    public String getUrl() {
        return imageBaseUrl + "/images/" + this.getPath() + this.getStoredName();
    }
}
