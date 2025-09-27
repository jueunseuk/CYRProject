package com.junsu.cyr.service.comment;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.comments.Fixed;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.comment.CommentRequest;
import com.junsu.cyr.model.comment.CommentResponse;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.CommentExceptionCode;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getComment())
                .fixed(Fixed.F)
                .locked(request.getLocked())
                .build();

        commentRepository.save(comment);
    }

    public List<CommentResponse> getPostComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment -> new CommentResponse(comment, comment.getUser(), post))
                .collect(Collectors.toList());
    }
}
