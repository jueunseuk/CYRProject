package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.user.UserProfileResponse;
import com.junsu.cyr.model.user.UserSidebarResponse;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.experience.ExperienceService;
import com.junsu.cyr.service.sand.SandService;
import com.junsu.cyr.service.temperature.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ExperienceService experienceService;
    private final SandService sandService;
    private final TemperatureService temperatureService;

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

        sandService.createSandLog(sand, user);
    }

    @Transactional
    public void addTemperature(User user, Integer temperatureId) {
        Temperature temperature = temperatureService.getTemperature(temperatureId);
        user.updateTemperature(temperature.getAmount());

        temperatureService.createTemperatureLog(user, temperature);
    }
}
