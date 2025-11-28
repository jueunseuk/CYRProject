package com.junsu.cyr.flow.post;

import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostFlow {

    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;

    @Transactional
    public void deletePost(Long postId, Integer userId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostByPostId(postId);

        if(post.getUser() != user) {
            throw new BaseException(PostExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        postRepository.delete(post);
        user.decreasePostCnt();
    }
}
