package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.model.user.*;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));
    }

    public SignupResponse getUserLocalStorageInfo(Integer userId) {
        User user = getUserById(userId);
        return new SignupResponse(user);
    }

    public boolean isLeastManager(User user) {
        return user.getRole() == Role.MANAGER || user.getRole() == Role.ADMIN;
    }

    public boolean isLeastAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public UserSidebarResponse getUserSidebar(Integer userId) {
        User user = getUserById(userId);
        return new UserSidebarResponse(userId, user.getEpxCnt(), user.getSand(), user.getGlass(), user.getTemperature(), user.getCreatedAt().toLocalDate(), user.getRole());
    }

    public UserProfileResponse getUserProfile(Integer userId) {
        User user = getUserById(userId);
        return new UserProfileResponse(user);
    }

    public OtherProfileResponse getOtherProfile(Integer otherId) {
        User user = getUserById(otherId);
        return new OtherProfileResponse(user);
    }

    public UserActivityResponse getUserActivityData(Integer userId) {
        User user = getUserById(userId);

        return new UserActivityResponse(user.getPostCnt(), user.getCommentCnt(), user.getEmpathyCnt(), user.getImageCnt());
    }

    public Long getUserCnt() {
        return userRepository.count();
    }

    public Long getUserCnt(LocalDateTime start, LocalDateTime now) {
        return userRepository.countByCreatedAtBetween(start, now);
    }

    public List<UserChatResponse> getUserList(UserConditionRequest condition, Integer userId) {
        getUserById(userId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        List<User> users;

        users = userRepository.findAllByUserId(userId, Status.DELETED, pageable);

        return users.stream().map(UserChatResponse::new).toList();
    }

    public List<User> getUserListByRole(Role role, UserConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return userRepository.findAllByRole(role, pageable);
    }

    public Page<User> searchByNickname(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return userRepository.findAllByNicknameContaining(condition.getKeyword(), pageable);
    }
}
