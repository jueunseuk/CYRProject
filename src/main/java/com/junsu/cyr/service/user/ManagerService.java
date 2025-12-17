package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.user.UserConditionRequest;
import com.junsu.cyr.model.user.UserManagementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserService userService;

    @ManagerOnly
    public List<UserManagementResponse> getMemberList(UserConditionRequest condition, Role role, Integer userId) {
        userService.getUserById(userId);

        List<User> users = userService.getUserListByRole(role, condition);

        return users.stream().map(UserManagementResponse::new).toList();
    }
}
