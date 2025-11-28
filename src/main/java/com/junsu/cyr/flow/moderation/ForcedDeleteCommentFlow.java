package com.junsu.cyr.flow.moderation;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ForcedDeleteCommentFlow {

    private final UserService userService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @Transactional
    public void forcedDeleteComment(Long commentId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Comment comment = commentService.getCommentByCommentId(commentId);

        commentRepository.delete(comment);
        comment.getPost().decreaseCommentCnt();
    }
}
