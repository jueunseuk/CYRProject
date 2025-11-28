package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileImageFlow {

    private final UserService userService;
    private final S3Service s3Service;

    @Transactional
    public String updateUserProfileImage(MultipartFile request, Integer userId) {
        User user = userService.getUserById(userId);

        if (request != null) {
            String profileUrl = s3Service.uploadFile(request, Type.PROFILE);
            user.updateProfileUrl(profileUrl);
        }

        return user.getProfileUrl();
    }
}
