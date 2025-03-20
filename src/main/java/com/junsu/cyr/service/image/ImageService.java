package com.junsu.cyr.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;



}
