package com.junsu.cyr.service.qna;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.domain.qnas.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.qna.QuestionResponse;
import com.junsu.cyr.model.qna.QuestionUploadRequest;
import com.junsu.cyr.repository.QuestionRepository;
import com.junsu.cyr.response.exception.code.QnaExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final SandRewardService sandRewardService;
    private final ExperienceRewardService experienceRewardService;
    private final UnlockAchievementFlow unlockAchievementFlow;

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id).
                orElseThrow(() -> new BaseException(QnaExceptionCode.NOT_FOUND_RESOURCE));
    }

    @Transactional
    public void createQuestion(QuestionUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Question question = Question.of(user, request.getTitle(), request.getContent(), request.getSandCnt());
        if(user.getSand() < question.getSandCnt()) {
            throw new BaseException(QnaExceptionCode.NOT_ENOUGH_SAND);
        }

        sandRewardService.addSand(user, 2, -question.getSandCnt());
        experienceRewardService.addExperience(user, 1);

        Long cnt = questionRepository.countQuestionByUser(user);
        unlockAchievementFlow.unlockAchievement(user, Type.QUESTION, Scope.TOTAL, cnt);

        questionRepository.save(question);
    }

    public List<QuestionResponse> getQuestions(String status, String sort, String direction) {
        List<Question> questions;
        if(status.equals("ALL")) {
            questions = questionRepository.findAllBy(PageableMaker.of(sort, direction));
        } else {
            Status s = Status.valueOf(status);
            questions = questionRepository.findAllByStatus(s, PageableMaker.of(sort, direction));
        }
        return questions.stream().map(QuestionResponse::new).toList();
    }

    @Transactional
    public void closeQuestionStatus(Integer userId, Long questionId) {
        User user = userService.getUserById(userId);
        Question question = findQuestionById(questionId);

        if(!user.equals(question.getUser())) {
            throw new BaseException(QnaExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        if(question.isClosed()) {
            throw new BaseException(QnaExceptionCode.INVALID_STATUS);
        }

        question.updateStatus(Status.CLOSED);
    }
}
