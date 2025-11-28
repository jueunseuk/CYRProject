package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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
    public String uploadFile(MultipartFile file, Type type) {
        String filePurpose = type.toString().toLowerCase();

        String fileName = filePurpose + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        try {
            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
    }

    @Transactional
    public List<String> uploadFiles(List<MultipartFile> files, Type type) {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            uploadedUrls.add(uploadFile(file, type));
        }

        return uploadedUrls;
    }

}
