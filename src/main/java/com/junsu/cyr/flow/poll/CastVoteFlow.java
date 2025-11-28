package com.junsu.cyr.flow.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.poll.PollLogService;
import com.junsu.cyr.service.poll.PollOptionService;
import com.junsu.cyr.service.poll.PollService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CastVoteFlow {

    private final UserService userService;
    private final PollService pollService;
    private final PollOptionService pollOptionService;
    private final PollLogService pollLogService;
    private final ExperienceRewardService experienceRewardService;
    private final SandRewardService sandRewardService;

    @Transactional
    public void castVote(Integer pollId, Long pollOptionId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = pollService.getPollByPollId(pollId);
        PollOption pollOption = pollOptionService.getPollOptionBYPollOptionId(pollOptionId);

        if(pollLogService.checkPollLogByUserAndPoll(user, poll)) {
            throw new BaseException(PollExceptionCode.ALREADY_PARTICIPATING_VOTE);
        }

        if(!pollOption.getPoll().equals(poll)) {
            throw new BaseException(PollExceptionCode.POLL_AND_OPTION_MISMATCH);
        }

        experienceRewardService.addExperience(user, 6);
        sandRewardService.addSand(user, 14);

        pollLogService.createPollLog(user, poll, pollOption);
    }
}
