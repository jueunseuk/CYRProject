package com.junsu.cyr.service.comment;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
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
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(CommentExceptionCode.NOT_FOUND_COMMENT));
    }

    @Transactional
    public Comment createComment(User user, Post post, String content, Boolean fixed, Boolean locked) {
        if(user == null || post == null || content == null || fixed == null || locked == null) {
            throw new BaseException(CommentExceptionCode.INVALID_VALUE_INJECTION);
        }

        if(content.length() < 5) {
            throw new BaseException(CommentExceptionCode.TOO_SHORT_COMMENT);
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .fixed(fixed)
                .locked(locked)
                .emoticon(null)
                .build();

        return commentRepository.save(comment);
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

    public Page<Comment> searchByContent(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return commentRepository.findAllByContentContaining(condition.getKeyword(), pageable);
    }
}
