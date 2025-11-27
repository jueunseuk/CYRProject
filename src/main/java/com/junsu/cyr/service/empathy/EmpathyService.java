package com.junsu.cyr.service.empathy;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.empathys.Empathy;
import com.junsu.cyr.domain.empathys.EmpathyId;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.empathy.EmpathyResponse;
import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.model.post.PostSearchConditionRequest;
import com.junsu.cyr.repository.EmpathyRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.EmpathyExceptionCode;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpathyService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final EmpathyRepository empathyRepository;
    private final UserRepository userRepository;
    private final UnlockAchievementFlow unlockAchievementFlow;

    @Transactional
    public EmpathyResponse createEmpathy(Long postId, Integer userId) {
        User user = userService.getUserById(userId);

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        EmpathyId empathyId = new EmpathyId(postId, userId);

        if (empathyRepository.existsById(empathyId)) {
            throw new BaseException(EmpathyExceptionCode.ALREADY_EMPATHIZE_POST);
        }

        Empathy empathy = Empathy.builder()
                .empathyId(empathyId)
                .post(post)
                .user(user)
                .build();

        empathyRepository.save(empathy);
        post.increaseEmpathyCnt();
        user.increaseEmpathyCnt();

        unlockAchievementFlow.achievementFlow(user, Type.EMPATHY, Scope.TOTAL, user.getEmpathyCnt());

        return new EmpathyResponse(postId, userId);
    }

    @Transactional
    public void deleteEmpathy(Long postId, Integer userId) {
        User user = userService.getUserById(userId);

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        EmpathyId empathyId = new EmpathyId(postId, userId);

        if (!empathyRepository.existsById(empathyId)) {
            throw new BaseException(EmpathyExceptionCode.NEVER_EMPATHIZE);
        }

        post.decreaseEmpathyCnt();
        user.decreaseEmpathyCnt();
        empathyRepository.deleteById(empathyId);
    }

    public Page<PostListResponse> getEmpathizePostByUser(Integer searchId, PostSearchConditionRequest condition) {
        User user = userRepository.findByUserId(searchId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort()));

        Page<Empathy> empathyList = empathyRepository.findAllByUser(user.getUserId(), pageable);
        return empathyList.map(e -> new PostListResponse(e.getPost()));
    }
}
