package com.junsu.cyr.flow.comment;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.model.comment.CommentRequest;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCommentFlow {

    private final UserService userService;
    private final PostService postService;
    private final ShopItemService shopItemService;
    private final CommentService commentService;
    private final ExperienceRewardService experienceRewardService;
    private final SandRewardService sandRewardService;
    private final UnlockAchievementFlow unlockAchievementFlow;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(CommentRequest request, Integer userId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostByPostId(request.getPostId());

        Comment comment = commentService.createComment(user, post, request.getComment(), Boolean.FALSE, request.getLocked());
        post.increaseCommentCnt();
        user.increaseCommentCnt();

        if(request.getShopItemId() != null) {
            ShopItem shopItem = shopItemService.getShopItemById(request.getShopItemId());
            comment.updateEmoticon(shopItem);
        }

        experienceRewardService.addExperience(user, 2);
        sandRewardService.addSand(user, 10);

        unlockAchievementFlow.unlockAchievement(user, Type.COMMENT, Scope.TOTAL, user.getCommentCnt());
        Long todayCommentCnt = commentRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        unlockAchievementFlow.unlockAchievement(user, Type.COMMENT, Scope.DAILY, todayCommentCnt);
    }
}
