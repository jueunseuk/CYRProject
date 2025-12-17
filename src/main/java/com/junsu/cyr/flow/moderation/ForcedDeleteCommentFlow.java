package com.junsu.cyr.flow.moderation;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.repository.CommentRepository;
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

    @ManagerOnly
    @Transactional
    public void forcedDeleteComment(Long commentId, Integer userId) {
        userService.getUserById(userId);

        Comment comment = commentService.getCommentByCommentId(commentId);

        commentRepository.delete(comment);
        comment.getPost().decreaseCommentCnt();
    }
}
