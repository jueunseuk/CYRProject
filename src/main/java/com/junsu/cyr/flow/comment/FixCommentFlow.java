package com.junsu.cyr.flow.comment;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.response.exception.code.CommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FixCommentFlow {

    private final UserService userService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @Transactional
    public void fixComment(Long commentId, Boolean fixed, Integer userId) {
        User user = userService.getUserById(userId);
        Comment comment = commentService.getCommentByCommentId(commentId);

        if(comment.getUser() != user) {
            throw new BaseException(CommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        if(fixed && commentRepository.countByPostAndFixed(comment.getPost(), Boolean.TRUE) > 3) {
            throw new BaseException(CommentExceptionCode.FIX_COMMENT_NUMBER_EXCEEDED);
        }

        comment.updateFixed(fixed);
    }
}
