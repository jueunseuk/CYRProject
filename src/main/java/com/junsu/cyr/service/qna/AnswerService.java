package com.junsu.cyr.service.qna;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.qnas.Answer;
import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.domain.qnas.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.qna.AnswerResponse;
import com.junsu.cyr.model.qna.AnswerUploadRequest;
import com.junsu.cyr.repository.AnswerRepository;
import com.junsu.cyr.response.exception.code.QnaExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerRepository answerRepository;
    private final SandRewardService sandRewardService;
    private final ExperienceRewardService experienceRewardService;
    private final UnlockAchievementFlow unlockAchievementFlow;

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id).
                orElseThrow(() -> new BaseException(QnaExceptionCode.NOT_FOUND_RESOURCE));
    }

    @Transactional
    public void createAnswer(Long questionId, Integer userId, AnswerUploadRequest request) {
        User user = userService.getUserById(userId);
        Question question = questionService.findQuestionById(questionId);

        if(question.isClosed()) {
            throw new BaseException(QnaExceptionCode.CLOSED_QUESTION);
        }

        Answer answer = Answer.of(user, question, request.getContent());
        answerRepository.save(answer);
        experienceRewardService.addExperience(user, 10);

        boolean isQuestionWriter = question.getUser().equals(user);
        boolean hasAlreadyAnswered = answerRepository.findAllByQuestion(question).stream().anyMatch(a -> a.getUser().equals(user));
        if(!isQuestionWriter && !hasAlreadyAnswered) {
            sandRewardService.addSand(user, 18);
        }

        Long cnt = answerRepository.countAnswerByUser(user);
        unlockAchievementFlow.unlockAchievement(user, Type.ANSWER, Scope.TOTAL, cnt);

        question.increaseAnswerCnt();
        if(question.getStatus() == Status.OPEN) question.updateStatus(Status.HOLD);
    }

    public List<AnswerResponse> getAllAnswerByQuestion(Integer userId, Long questionId) {
        userService.getUserById(userId);
        Question question = questionService.findQuestionById(questionId);

        List<Answer> answers = answerRepository.findAllByQuestion(question);

        return answers.stream().map(AnswerResponse::new).toList();
    }

    @Transactional
    public void adoptAnswer(Integer userId, Long questionId, Long answerId) {
        User user = userService.getUserById(userId);
        Question question = questionService.findQuestionById(questionId);

        Answer answer = findAnswerById(answerId);

        if(!answer.getQuestion().equals(question)) {
            throw new BaseException(QnaExceptionCode.MISMATCH_QUESTION_AND_ANSWER);
        }

        if(question.getUser().equals(user)) {
            throw new BaseException(QnaExceptionCode.CANNOT_ADOPT_MYSELF);
        }

        if(answerRepository.existsByAdoptAnswer(question)) {
            throw new BaseException(QnaExceptionCode.ALREADY_ADOPT_ANSWER_EXIST);
        }

        answer.adopt();
        User adoptUser = answer.getUser();
        int bounty = Math.max(
                1,
                (int) Math.floor(question.getSandCnt() * 0.9)
        );
        sandRewardService.addSand(adoptUser, 10, bounty);
        question.updateStatus(Status.RESOLVED);
    }
}
