package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.AchievementLog;
import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.notification.usecase.UserNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RefreshAchievementFlow {

    private final UserService userService;
    private final GlassService glassService;
    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final EmpathyRepository empathyRepository;
    private final GlassLogRepository glassLogRepository;
    private final QuestionRepository questionRepository;
    private final UserNotificationUseCase userNotificationUseCase;
    private final AchievementRepository achievementRepository;
    private final AchievementLogRepository achievementLogRepository;
    private final UnlockAchievementFlow unlockAchievementFlow;

    @Transactional
    public Long refreshAchievement(Integer userId) {
        User user = userService.getUserById(userId);

        long sum = 0L;

        Long postCnt = postRepository.countByUser(user);
        sum += findOutAchievement(user, Type.POST, Scope.TOTAL, postCnt);

        Long commentCnt = commentRepository.countByUser(user);
        sum += findOutAchievement(user, Type.COMMENT, Scope.TOTAL, commentCnt);

        Long empathyCnt = empathyRepository.countByUser(user);
        sum += findOutAchievement(user, Type.EMPATHY, Scope.TOTAL, empathyCnt);

        Long questionCnt = questionRepository.countByUser(user);
        sum += findOutAchievement(user, Type.QUESTION, Scope.TOTAL, questionCnt);

        Long answerCnt = answerRepository.countByUser(user);
        sum += findOutAchievement(user, Type.ANSWER, Scope.TOTAL, answerCnt);

        Long craftCnt = glassLogRepository.countByUserAndGlass(user, glassService.getGlass(1));
        sum += findOutAchievement(user, Type.CRAFTSHOP, Scope.TOTAL, craftCnt);

        Long attendanceCnt = user.getAttendanceCnt().longValue();
        sum += findOutAchievement(user, Type.ATTENDANCE, Scope.TOTAL, attendanceCnt);

        Long maxConsecutiveAttendanceCnt = user.getMaxConsecutiveAttendanceCnt().longValue();
        sum += findOutAchievement(user, Type.ATTENDANCE, Scope.STREAK, maxConsecutiveAttendanceCnt);

        Long cheerCnt = user.getCheerCnt();
        sum += findOutAchievement(user, Type.CHEER, Scope.TOTAL, cheerCnt);

        userNotificationUseCase.refreshAchievement(user, sum);

        return sum;
    }

    private Long findOutAchievement(User user, Type type, Scope scope, Long amount) {
        Long sum = 0L;

        List<Achievement> achievements = achievementRepository.findAllBySatisfyCondition(type, scope, amount);
        List<AchievementLog> unlockAchievements = achievementLogRepository.findAllByUnlock(user, type, scope);

        Set<Integer> set = new HashSet<>();
        for(AchievementLog al : unlockAchievements) {
            set.add(al.getAchievement().getAchievementId());
        }

        for(Achievement a : achievements) {
            if(!set.contains(a.getAchievementId())) {
                unlockAchievementFlow.unlockAchievement(user, type, scope, a.getConditionAmount());
                sum++;
            }
        }

        return sum;
    }
}
