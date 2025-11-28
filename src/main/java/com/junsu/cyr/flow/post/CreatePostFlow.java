package com.junsu.cyr.flow.post;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.post.PostUploadRequest;
import com.junsu.cyr.model.post.PostUploadResponse;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.board.BoardService;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreatePostFlow {

    private final UserService userService;
    private final BoardService boardService;
    private final PostRepository postRepository;
    private final UnlockAchievementFlow unlockAchievementFlow;
    private final ExperienceRewardService experienceRewardService;
    private final SandRewardService sandRewardService;

    @Transactional
    public PostUploadResponse createPost(PostUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Board board = boardService.findBoardByBoardId(Integer.parseInt(request.getBoardId()));

        if(request.getContent() == null){
            throw new BaseException(PostExceptionCode.CONTENT_IS_EMPTY);
        }

        Post post = Post.create(user, board, request.getTitle(), request.getContent(), request.getLocked());

        postRepository.save(post);
        user.increasePostCnt();

        unlockAchievementFlow.unlockAchievement(user, Type.POST, Scope.TOTAL, user.getPostCnt());
        Long todayPostCnt = postRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        unlockAchievementFlow.unlockAchievement(user, Type.POST, Scope.DAILY, todayPostCnt);

        switch(board.getBoardId()) {
            case 9 -> sandRewardService.addSand(user, 1);
            case 10 -> sandRewardService.addSand(user, 2);
            case 11 -> sandRewardService.addSand(user, 3);
            case 12 -> sandRewardService.addSand(user, 4);
            case 13 -> sandRewardService.addSand(user, 5);
            case 14 -> sandRewardService.addSand(user, 6);
            case 15 -> sandRewardService.addSand(user, 7);
            case 16 -> sandRewardService.addSand(user, 8);
            case 17 -> sandRewardService.addSand(user, 9);
            default -> sandRewardService.addSand(user, 10);
        }
        experienceRewardService.addExperience(user, 1);

        return new PostUploadResponse(post.getBoard(), post.getPostId());
    }
}
