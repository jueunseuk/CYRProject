package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AllocateUserStatusFlow {

    private final UserService userService;

    @ManagerOnly
    @Transactional
    public void allocateUserStatus(Integer memberId, Status status, Integer userId) {
        userService.getUserById(userId);

        User member = userService.getUserById(memberId);
        member.updateStatus(status);
    }
}
