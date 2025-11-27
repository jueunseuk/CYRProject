package com.junsu.cyr.service.comment;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.comment.CommentRequest;
import com.junsu.cyr.model.comment.CommentResponse;
import com.junsu.cyr.model.comment.CommentSearchConditionRequest;
import com.junsu.cyr.model.comment.UserCommentResponse;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.CommentExceptionCode;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final UnlockAchievementFlow unlockAchievementFlow;
    private final ShopItemService shopItemService;

    private Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(CommentExceptionCode.NOT_FOUND_COMMENT));
    }

    @Transactional
    public void uploadComment(CommentRequest request, Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Post post = postRepository.findByPostId(request.getPostId())
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        if(request.getComment().length() < 5) {
            throw new BaseException(CommentExceptionCode.TOO_SHORT_COMMENT);
        }

        post.increaseCommentCnt();
        user.increaseCommentCnt();

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getComment())
                .fixed(Boolean.FALSE)
                .locked(request.getLocked())
                .build();

        if(request.getShopItemId() != null) {
            ShopItem shopItem = shopItemService.getShopItemById(request.getShopItemId());
            if(shopItem.getShopCategory().getShopCategoryId() != 1) {
                throw new BaseException(CommentExceptionCode.INVALID_EMOTICON_ID);
            }
            comment.updateEmoticon(shopItem);
        }

        userService.addExpAndSand(user, 2, 10);

        commentRepository.save(comment);

        unlockAchievementFlow.unlockAchievement(user, Type.COMMENT, Scope.TOTAL, user.getCommentCnt());
        Long todayCommentCnt = commentRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        unlockAchievementFlow.unlockAchievement(user, Type.COMMENT, Scope.DAILY, todayCommentCnt);
    }

    public List<CommentResponse> getPostComments(Long postId, Boolean fixed) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        List<Comment> comments;
        if(fixed) {
            comments = commentRepository.findByPostAndFixed(post, Boolean.TRUE, PageableMaker.of("createdAt", PageableMaker.ASC));
        } else {
            comments = commentRepository.findByPostAndFixed(post, Boolean.FALSE,  PageableMaker.of("createdAt", PageableMaker.ASC));
        }

        return comments.stream()
                .map(comment -> new CommentResponse(comment, comment.getUser(), post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(CommentRequest request, Long commentId, Integer userId) {
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        if(request.getComment().length() < 5) {
            throw new BaseException(CommentExceptionCode.TOO_SHORT_COMMENT);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(CommentExceptionCode.NOT_FOUND_COMMENT));

        if(comment.getUser().getUserId() != userId) {
            throw new BaseException(CommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        comment.update(request.getComment(), request.getLocked());
    }

    @Transactional
    public void deleteComment(Long commentId, Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(CommentExceptionCode.NOT_FOUND_COMMENT));

        if(comment.getUser().getUserId() != userId) {
            throw new BaseException(CommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        commentRepository.delete(comment);
        comment.getPost().decreaseCommentCnt();
        user.decreaseCommentCnt();
    }

    public Page<UserCommentResponse> getCommentsByUser(Integer searchId, Integer userId, CommentSearchConditionRequest condition) {
        User user = userRepository.findByUserId(searchId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort()));

        Page<Comment> userCommentResponses;

        if(searchId.equals(userId)) {
            userCommentResponses = commentRepository.findAllByUser(user, pageable);
        } else {
            userCommentResponses = commentRepository.findAllByUserAndLocked(user, Boolean.FALSE, pageable);
        }

        return userCommentResponses.map(UserCommentResponse::new);
    }

    public Long getCommentCnt() {
        return commentRepository.count();
    }

    public Long getCommentCnt(LocalDateTime start, LocalDateTime now) {
        return commentRepository.countByCreatedAtBetween(start, now);
    }

    @Transactional
    public void deleteCommentForce(Long commentId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Comment comment = getCommentByCommentId(commentId);

        commentRepository.delete(comment);
        comment.getPost().decreaseCommentCnt();
    }

    public Page<Comment> searchByContent(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return commentRepository.findAllByContentContaining(condition.getKeyword(), pageable);
    }

    @Transactional
    public void updateFix(Long commentId, Boolean fixed, Integer userId) {
        User user = userService.getUserById(userId);
        Comment comment = getCommentByCommentId(commentId);

        if(comment.getUser() != user) {
            throw new BaseException(CommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        if(comment.getFixed() == fixed) {
            throw new BaseException(CommentExceptionCode.INVALID_REQUEST);
        }

        if(fixed && commentRepository.countByPostAndFixed(comment.getPost(), Boolean.TRUE) > 3) {
            throw new BaseException(CommentExceptionCode.FIX_COMMENT_NUMBER_EXCEEDED);
        }

        comment.updateFixed(fixed);
    }
}
