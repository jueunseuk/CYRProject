package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.user.UserProfileUpdateRequest;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserInformationFlow {

    private final UserService userService;

    @Transactional
    public UserProfileUpdateRequest updateUserInformation(UserProfileUpdateRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        user.updateInformation(request.getAge(), request.getNickname(), request.getGender(), request.getIntroduction(), request.getName());

        return new UserProfileUpdateRequest(
                user.getAge(),
                user.getNickname(),
                user.getGender(),
                user.getIntroduction(),
                user.getName());
    }
}
