package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, Type type) throws IOException {
        String fileName = System.currentTimeMillis() + "." + file.getOriginalFilename();
        String filePurpose;

        switch (type) {
            case PROFILE -> filePurpose = "profile";
            case POST -> filePurpose = "post";
            case COMMENT -> filePurpose = "comment";
            case COMPLAINT -> filePurpose = "complaint";
            default -> filePurpose = "unknown";
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return "https://" + bucket + ".s3.amazonaws.com/" + filePurpose + "/" + fileName;
    }
}
