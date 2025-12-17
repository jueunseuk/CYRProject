package com.junsu.cyr.flow.moderation;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.empathys.Empathy;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.repository.EmpathyRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForcedDeletePostFlow {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final EmpathyRepository empathyRepository;
    private final PostRepository postRepository;

    @ManagerOnly
    @Transactional
    public void forcedDeletePost(Long postId, Integer userId) {
        userService.getUserById(userId);

        Post post = postService.getPostByPostId(postId);

        List<Comment> comments = commentRepository.findByPost(post);
        List<Empathy> empathyList = empathyRepository.findAllByPost(post);

        commentRepository.deleteAll(comments);
        empathyRepository.deleteAll(empathyList);
        postRepository.delete(post);
    }
}
