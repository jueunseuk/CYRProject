package com.junsu.cyr.service.image;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final UserService userService;

    public String uploadImage(MultipartFile image, Type type, Integer userId) {
        User user = userService.getUserById(userId);

        String imageUrl = null;
        try {
            imageUrl = s3Service.uploadFile(image, type);
        } catch (IOException e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        return imageUrl;
    }
}