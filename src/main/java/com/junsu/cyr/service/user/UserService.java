package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.user.UserProfileResponse;
import com.junsu.cyr.model.user.UserSidebarResponse;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));
    }

    public Long getUserExp(Integer userid) {
        User user = getUserById(userid);

        return user.getEpxCnt();
    }


    public UserSidebarResponse getUserSidebar(Integer userId) {
        User user = getUserById(userId);

        return new UserSidebarResponse(userId, user.getEpxCnt(), user.getSand(), user.getGlass(), user.getTemperature());
    }

    public UserProfileResponse getUserProfile(Integer userId) {
        User user = getUserById(userId);

        return new UserProfileResponse(user);
    }
}
