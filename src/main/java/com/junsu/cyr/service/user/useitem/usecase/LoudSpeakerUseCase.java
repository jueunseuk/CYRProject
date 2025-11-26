package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.post.ExposurePostService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("LOUD_SPEAKER")
@RequiredArgsConstructor
public class LoudSpeakerUseCase implements UseConsumableItem {

    private final PostService postService;
    private final ExposurePostService exposurePostService;

    @Override
    @Transactional
    public ItemUseResult use(User user, ItemUseRequest request) {
        if(request.getPostId() == null) {
            throw new BaseException(UserInventoryExceptionCode.INVALID_USE_REQUEST);
        }

        Post post = postService.getPostByPostId(request.getPostId());

        exposurePostService.exposingPosts(post);

        return ItemUseResult.builder()
                .success(true)
                .message("success to use loud speaker")
                .data(post.getTitle())
                .type("LOUD_SPEAKER")
                .build();
    }
}
