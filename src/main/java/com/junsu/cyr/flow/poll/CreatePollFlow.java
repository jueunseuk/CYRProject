package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollUploadRequest;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.poll.PollOptionService;
import com.junsu.cyr.service.poll.PollService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePollFlow {

    private final UserService userService;
    private final S3Service s3Service;
    private final PollOptionService pollOptionService;
    private final PollService pollService;

    @Transactional
    public void createPoll(PollUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        Poll poll = pollService.createPoll(user, request.getTitle(), request.getDescription(), request.getClosedAt());

        if(request.getFile() != null) {
            String imageUrl = s3Service.uploadFile(request.getFile(), Type.POLL);
            poll.updateImageUrl(imageUrl);
        }

        pollOptionService.createPollOptions(poll, request.getOptions());
    }
}
