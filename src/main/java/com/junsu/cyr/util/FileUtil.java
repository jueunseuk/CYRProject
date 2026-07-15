package com.junsu.cyr.util;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String[] storeFile(MultipartFile file, Type imageType) {
        String relativePath = imageType.name();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path targetLocation = Paths.get(uploadDir).resolve(relativePath).resolve(fileName);

        try {
            Files.createDirectories(targetLocation.getParent());

            file.transferTo(targetLocation.toFile()); // save to device
            log.info("Success to save image - saveDir: {}", targetLocation);
            return new String[] {relativePath, fileName};
        } catch (IOException e) {
            log.warn("Error saving file");
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }
    }
}