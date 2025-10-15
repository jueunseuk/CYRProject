package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadFile(MultipartFile file, Type type) throws IOException {
        String filePurpose;

        switch (type) {
            case PROFILE -> filePurpose = "profile";
            case POST -> filePurpose = "post";
            case COMMENT -> filePurpose = "comment";
            case COMPLAINT -> filePurpose = "complaint";
            case CYR -> filePurpose = "cyr";
            case SHOP -> filePurpose = "shop";
            default -> filePurpose = "unknown";
        }

        String fileName = filePurpose + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
    }

    @Transactional
    public List<String> uploadFiles(List<MultipartFile> files, Type type) throws IOException {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            uploadedUrls.add(uploadFile(file, type));
        }

        return uploadedUrls;
    }

}
