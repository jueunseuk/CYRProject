package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollOptionCount;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.poll.PollLogService;
import com.junsu.cyr.service.poll.PollOptionService;
import com.junsu.cyr.service.poll.PollService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregatePollFlow {

    private final UserService userService;
    private final PollService pollService;
    private final PollLogService pollLogService;
    private final PollOptionService pollOptionService;

    @Transactional
    public List<PollOptionCount> aggregatePoll(Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = pollService.getPollByPollId(pollId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        if(poll.getStatus() != Status.CLOSED) {
            throw new BaseException(PollExceptionCode.UNABLE_TO_AGGREGATE_POLL_STATE);
        }

        List<PollOptionCount> pollLogs = pollLogService.getPollResult(poll);

        if (pollLogs.isEmpty()) {
            throw new BaseException(PollExceptionCode.NO_VOTES_TO_AGGREGATE);
        }

        PollOptionCount max = pollLogs.getFirst();
        for(PollOptionCount pollOptionCount : pollLogs) {
            PollOption pollOption = pollOptionService.getPollOptionBYPollOptionId(pollOptionCount.getPollOptionId());
            pollOption.updateVoteCount(pollOptionCount.getVoteCount());

            if(pollOption.getVoteCount() > max.getVoteCount()) {
                max = pollOptionCount;
            }
        }

        poll.updateResult(max.getPollOptionId(), max.getVoteCount());

        pollLogs.sort((o1, o2) -> {
            if (o1.getVoteCount().equals(o2.getVoteCount())) {
                return Long.compare(o1.getPollOptionId(), o2.getPollOptionId());
            }
            return Long.compare(o2.getVoteCount(), o1.getVoteCount());
        });

        return pollLogs;
    }
}
