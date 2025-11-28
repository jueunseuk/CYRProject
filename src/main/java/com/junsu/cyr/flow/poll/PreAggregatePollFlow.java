package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollOptionCount;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.poll.PollLogService;
import com.junsu.cyr.service.poll.PollService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreAggregatePollFlow {

    private final UserService userService;
    private final PollService pollService;
    private final PollLogService pollLogService;

    public List<PollOptionCount> preAggregatePoll(Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = pollService.getPollByPollId(pollId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        if(poll.getStatus() != Status.IN_PROGRESS) {
            throw new BaseException(PollExceptionCode.UNABLE_TO_AGGREGATE_POLL_STATE);
        }

        List<PollOptionCount> pollLogs = pollLogService.getPollResult(poll);

        if (pollLogs.isEmpty()) {
            throw new BaseException(PollExceptionCode.NO_VOTES_TO_AGGREGATE);
        }

        return pollLogs;
    }
}
