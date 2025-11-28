package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.user.UserActivityResponse;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.repository.EmpathyRepository;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.service.notification.usecase.UserNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshActivityFlow {

    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final EmpathyRepository empathyRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final UserNotificationUseCase userNotificationUseCase;

    @Transactional
    public UserActivityResponse refreshActivity(Integer userId) {
        User user = userService.getUserById(userId);

        Long postCnt = postRepository.countByUser(user);
        Long commentCnt = commentRepository.countByUser(user);
        Long empathyCnt = empathyRepository.countByUser(user);
        Long imageCnt = galleryImageRepository.countByUser(user);

        user.updateActivity(postCnt, commentCnt, empathyCnt, imageCnt);

        userNotificationUseCase.refreshActivity(user);
        return new UserActivityResponse(postCnt, commentCnt, empathyCnt, imageCnt);
    }
}
