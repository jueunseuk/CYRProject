package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.user.UserConditionRequest;
import com.junsu.cyr.model.user.UserManagementResponse;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    public List<UserManagementResponse> getManagerList(UserConditionRequest condition, Role role, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }

        List<User> users = userService.getUserListByRole(role, condition);

        return users.stream().map(UserManagementResponse::new).toList();
    }

    @Transactional
    public void updateStatus(Integer memberId, Status status, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }

        User member = userService.getUserById(memberId);
        if(member.getStatus().equals(status)) {
            throw new BaseException(UserExceptionCode.INCORRECT_ROLE_CHANGE_REQUEST);
        }

        member.updateStatus(status);
    }

    @Transactional
    public void updateRole(Integer memberId, Role role, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }

        User member = userService.getUserById(memberId);
        if(member.getRole().equals(role)) {
            throw new BaseException(UserExceptionCode.INCORRECT_ROLE_CHANGE_REQUEST);
        }

        member.updateRole(role);
    }
}
