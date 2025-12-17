package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.poll.PollUpdateRequest;
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

    @ManagerOnly
    @Transactional
    public void updatePollStatus(PollUpdateRequest request, Integer pollId, Integer userId) {
        userService.getUserById(userId);
        Poll poll = pollService.getPollByPollId(pollId);

        poll.updateStatus(request.getStatus());
    }
}
