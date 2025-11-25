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
public class ManagerService {

    private final UserService userService;

    public List<UserManagementResponse> getMemberList(UserConditionRequest condition, Role role, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        List<User> users = userService.getUserListByRole(role, condition);

        return users.stream().map(UserManagementResponse::new).toList();
    }

    @Transactional
    public void updateWarnCnt(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateWarnCnt(amount);

        userService.addSand(member, 16);
    }

    @Transactional
    public void updateGlass(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateGlass(amount);

        userService.addGlass(member, 5, amount);
    }

    @Transactional
    public void updateSand(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateSand(amount);

        userService.addSand(member, 18, amount);
    }

    @Transactional
    public void updateTemperature(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        if(amount % 50 != 0) {
            throw new BaseException(UserExceptionCode.CAN_ONLY_BE_CHANGED_TO_50_UNITS);
        }

        User member = userService.getUserById(memberId);
        member.updateTemperature(amount);

        if(amount > 0) {
            userService.addTemperature(member, 100);
        } else {
            userService.addTemperature(member, 101);
        }
    }

    @Transactional
    public void updateStatus(Integer memberId, Status status, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateStatus(status);
    }
}
