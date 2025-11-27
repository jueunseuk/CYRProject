package com.junsu.cyr.flow.user.attendance;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.attendance.AttendanceRequest;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.response.exception.code.AttendanceExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.attendance.AttendanceService;
import com.junsu.cyr.service.experience.ExperienceService;
import com.junsu.cyr.service.notification.usecase.TemperatureNotificationUseCase;
import com.junsu.cyr.service.sand.SandService;
import com.junsu.cyr.service.temperature.TemperatureService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakeAttendanceFlow {

    private final UserService userService;
    private final AttendanceRepository attendanceRepository;
    private final ExperienceService experienceService;
    private final SandService sandService;
    private final TemperatureService temperatureService;
    private final TemperatureNotificationUseCase temperatureNotificationUseCase;
    private final AttendanceService attendanceService;
    private final UnlockAchievementFlow unlockAchievementFlow;

    @Transactional
    public void makeAttendance(Integer userId, AttendanceRequest request) {
        User user = userService.getUserById(userId);
        AttendanceId attendanceId = new AttendanceId(user.getUserId(), LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
        if(attendance.isPresent()) {
            throw new BaseException(AttendanceExceptionCode.ALREADY_ATTEND_USER);
        }

        Experience experience = experienceService.getExperience(4);
        user.increaseExpCnt(experience.getAmount());
        experienceService.createExperienceLog(experience, user);

        Sand sand = sandService.getSand(12);
        user.updateSand(sand.getAmount());
        sandService.createSandLog(sand, sand.getAmount(), user);

        long gap = getGapWithLastAttendanceDate(user);

        Temperature temperature;
        if(gap == 0) {
            temperature = temperatureService.getTemperature(1);
            user.updateTemperature(temperature.getAmount());
            temperatureService.createTemperatureLog(user, temperature);
            user.initConsecutiveAttendanceCnt();
        } else if(gap == 1) {
            temperature = temperatureService.getTemperature(2);
            user.updateTemperature(temperature.getAmount());
            temperatureService.createTemperatureLog(user, temperature);
            user.increaseConsecutiveAttendanceCnt();
        } else if(gap >= 2) {
            user.initConsecutiveAttendanceCnt();
            if(gap <= 3) {
                temperature = temperatureService.getTemperature(7);
                user.updateTemperature(temperature.getAmount());
                temperatureService.createTemperatureLog(user, temperature);
            } else if(gap <= 30) {
                temperature = temperatureService.getTemperature(8);
                user.updateTemperature(temperature.getAmount());
                temperatureService.createTemperatureLog(user, temperature);
            } else {
                temperature = temperatureService.getTemperature(9);
                user.updateTemperature(temperature.getAmount());
                temperatureService.createTemperatureLog(user, temperature);
            }
        }

        if(user.getTemperature() == 1800) {
            if(user.getSand() < 100) {
                temperatureNotificationUseCase.temperatureMaximumReached(user);
            } else {
                temperatureNotificationUseCase.temperatureMaximumReachAndCraftGlass(user);
            }
        }

        if(user.getConsecutiveAttendanceCnt() % 7 == 0) {
            temperature = temperatureService.getTemperature(3);
            user.updateTemperature(temperature.getAmount());
        } else if(user.getConsecutiveAttendanceCnt() % 30 == 0) {
            temperature = temperatureService.getTemperature(4);
            user.updateTemperature(temperature.getAmount());
        } else if(user.getConsecutiveAttendanceCnt() % 100 == 0) {
            temperature = temperatureService.getTemperature(5);
            user.updateTemperature(temperature.getAmount());
        } else if(user.getConsecutiveAttendanceCnt() % 365 == 0) {
            temperature = temperatureService.getTemperature(6);
            user.updateTemperature(temperature.getAmount());
        }

        if(request.getComment() == null || request.getComment().isEmpty()) {
            throw new BaseException(AttendanceExceptionCode.CONTENT_DOES_NOT_EXISTS);
        }

        attendanceService.createAttendance(attendanceId, request.getComment());
        user.updateAttendanceCnt();

        unlockAchievementFlow.unlockAchievement(user, Type.ATTENDANCE, Scope.TOTAL, Long.valueOf(user.getAttendanceCnt()));
        unlockAchievementFlow.unlockAchievement(user, Type.ATTENDANCE, Scope.STREAK, Long.valueOf(user.getConsecutiveAttendanceCnt()));
        LocalDate now = LocalDate.now();
        if(now.getMonthValue() == 2 && now.getDayOfMonth() == 24) {
            unlockAchievementFlow.unlockAchievement(user, Type.ATTENDANCE, Scope.DATE, 1L);
        } else if(now.getMonthValue() == 11 && now.getDayOfMonth() == 24) {
            unlockAchievementFlow.unlockAchievement(user, Type.ATTENDANCE, Scope.DATE, 2L);
        }
    }

    private long getGapWithLastAttendanceDate(User user) {
        Optional<Attendance> getAttendance = getLastAttendanceDate(user.getUserId());
        if(getAttendance.isEmpty()) {
            return 0;
        }

        Attendance lastAttendance = getAttendance.get();
        LocalDate lastDate = lastAttendance.getCreatedAt().toLocalDate();
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(lastDate, today);
    }

    public Optional<Attendance> getLastAttendanceDate(Integer userId) {
        return attendanceRepository.findTopByAttendanceIdUserIdOrderByCreatedAtDesc(userId);
    }
}
