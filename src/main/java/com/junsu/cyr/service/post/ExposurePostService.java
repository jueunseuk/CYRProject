package com.junsu.cyr.service.post;

import com.junsu.cyr.domain.posts.ExposurePost;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.repository.ExposurePostRepository;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExposurePostService {

    private final ExposurePostRepository exposurePostRepository;
    private final UserService userService;

    @Transactional
    public void exposingPosts(Post post) {
        ExposurePost exposurePost = ExposurePost.builder().post(post).build();
        exposurePostRepository.save(exposurePost);
    }

    @Transactional
    public void concealingPosts(Long hours) {
        LocalDateTime before = LocalDateTime.now().minusHours(hours);
        exposurePostRepository.deleteByCreatedAtBefore(before);
    }

    public List<PostListResponse> getExposedPosts(Integer userId) {
        userService.getUserById(userId);

        List<ExposurePost> exposurePosts = exposurePostRepository.findAll(PageableMaker.of("createdAt", PageableMaker.ASC)).getContent();

        return exposurePosts.stream().map(exposurePost -> new PostListResponse(exposurePost.getPost())).toList();
    }
}
