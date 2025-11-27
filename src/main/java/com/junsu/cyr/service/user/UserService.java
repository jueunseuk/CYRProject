package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.auth.SignupResponse;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.model.user.*;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.experience.ExperienceService;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.sand.SandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ExperienceService experienceService;
    private final SandService sandService;
    private final S3Service s3Service;

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

    @Transactional
    public void addExpAndSand(User user, Integer experienceId, Integer sandId) {
        addExperience(user, experienceId);
        addSand(user, sandId);
    }

    @Transactional
    public void addExperience(User user, Integer experienceId) {
        Experience experience = experienceService.getExperience(experienceId);
        user.increaseExpCnt(experience.getAmount());

        experienceService.createExperienceLog(experience, user);
    }

    @Transactional
    public void addSand(User user, Integer sandId) {
        Sand sand = sandService.getSand(sandId);
        user.updateSand(sand.getAmount());

        sandService.createSandLog(sand, sand.getAmount(), user);
    }

    public OtherProfileResponse getOtherProfile(Integer otherId) {
        User user = getUserById(otherId);
        return new OtherProfileResponse(user);
    }

    @Transactional
    public UserProfileUpdateRequest updateUserInformation(UserProfileUpdateRequest request, Integer userId) {
        User user = getUserById(userId);

        if(request.getIntroduction() != null && request.getIntroduction().length() < 5) {
            throw new BaseException(UserExceptionCode.TOO_SHORT_INTRODUCTION);
        }

        user.updateInformation(request);

        return new UserProfileUpdateRequest(
                user.getAge(),
                user.getNickname(),
                user.getGender(),
                user.getIntroduction(),
                user.getName());
    }

    @Transactional
    public String updateUserProfileImage(MultipartFile request, Integer userId) {
        User user = getUserById(userId);

        try {
            if (request != null) {
                String profileUrl = s3Service.uploadFile(request, Type.PROFILE);
                user.updateProfileUrl(profileUrl);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        return user.getProfileUrl();
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
