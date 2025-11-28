package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollUpdateRequest;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.poll.PollService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePollStatusFlow {

    private final UserService userService;
    private final PollService pollService;

    @Transactional
    public void updatePollStatus(PollUpdateRequest request, Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = pollService.getPollByPollId(pollId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        poll.updateStatus(request.getStatus());
    }
}
