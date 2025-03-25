package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.User;
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


}
