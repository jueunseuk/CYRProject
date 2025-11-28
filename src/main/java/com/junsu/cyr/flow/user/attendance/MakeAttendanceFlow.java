package com.junsu.cyr.flow.user.attendance;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.attendance.AttendanceRequest;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.response.exception.code.AttendanceExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.attendance.AttendanceService;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.notification.usecase.TemperatureNotificationUseCase;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.temperature.TemperatureRewardService;
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
    private final TemperatureNotificationUseCase temperatureNotificationUseCase;
    private final AttendanceService attendanceService;
    private final UnlockAchievementFlow unlockAchievementFlow;
    private final ExperienceRewardService experienceRewardService;
    private final SandRewardService sandRewardService;
    private final TemperatureRewardService temperatureRewardService;

    @Transactional
    public void makeAttendance(Integer userId, AttendanceRequest request) {
        User user = userService.getUserById(userId);
        AttendanceId attendanceId = new AttendanceId(user.getUserId(), LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
        if(attendance.isPresent()) {
            throw new BaseException(AttendanceExceptionCode.ALREADY_ATTEND_USER);
        }

        experienceRewardService.addExperience(user, 4);

        sandRewardService.addSand(user, 12);

        long gap = getGapWithLastAttendanceDate(user);

        Temperature temperature;
        if(gap == 0) {
            temperatureRewardService.addTemperature(user, 1);
            user.initConsecutiveAttendanceCnt();
        } else if(gap == 1) {
            temperatureRewardService.addTemperature(user, 2);
            user.increaseConsecutiveAttendanceCnt();
        } else if(gap >= 2) {
            user.initConsecutiveAttendanceCnt();
            if(gap <= 3) {
                temperatureRewardService.addTemperature(user, 7);
            } else if(gap <= 30) {
                temperatureRewardService.addTemperature(user, 8);
            } else {
                temperatureRewardService.addTemperature(user, 9);
            }
        }

        if(user.getTemperature() == 1800) {
            if(user.getSand() < 100) {
                temperatureNotificationUseCase.temperatureMaximumReached(user);
            } else {
                temperatureNotificationUseCase.temperatureMaximumReachAndCraftGlass(user);
            }
        }

        boolean flag = false;
        int amount = 0;
        if(user.getConsecutiveAttendanceCnt() % 7 == 0) {
            temperatureRewardService.addTemperature(user, 3);
            flag = true;
        } else if(user.getConsecutiveAttendanceCnt() % 30 == 0) {
            temperatureRewardService.addTemperature(user, 4);
            flag = true;
        } else if(user.getConsecutiveAttendanceCnt() % 100 == 0) {
            temperatureRewardService.addTemperature(user, 5);
            flag = true;
        } else if(user.getConsecutiveAttendanceCnt() % 365 == 0) {
            temperatureRewardService.addTemperature(user, 6);
            flag = true;
        }

        if(flag) {
            temperatureNotificationUseCase.additionalTemperatureReached(user);
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
