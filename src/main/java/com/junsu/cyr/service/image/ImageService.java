package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final UserService userService;

    public String uploadImage(MultipartFile image, Type type, Integer userId) {
        userService.getUserById(userId);
        return s3Service.uploadFile(image, type);
    }
}