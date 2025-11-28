package com.junsu.cyr.service.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollLog;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.*;
import com.junsu.cyr.repository.PollRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final UserService userService;
    private final PollRepository pollRepository;
    private final PollOptionService pollOptionService;
    private final PollLogService pollLogService;

    public Poll getPollByPollId(Integer pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new BaseException(PollExceptionCode.NOT_FOUND_POLL));
    }

    @Transactional
    public Poll createPoll(User user, String title, String description, LocalDateTime closedAt) {
        if(title.isEmpty() || description.isEmpty() || closedAt == null) {
            throw new BaseException(PollExceptionCode.INVALID_VALUE_INJECTION);
        }

        if(closedAt.isBefore(LocalDateTime.now())) {
            throw new BaseException(PollExceptionCode.INVALID_CLOSED_AT);
        }

        Poll poll = Poll.builder()
                .user(user)
                .title(title)
                .description(description)
                .closedAt(closedAt)
                .status(Status.IN_PROGRESS)
                .build();

        return pollRepository.save(poll);
    }

    public PollResponse getPoll(Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = getPollByPollId(pollId);
        
        List<PollOption> pollOptions = pollOptionService.getPollOptionsByPoll(poll);
        List<PollOptionResponse> pollOptionResponses = pollOptions.stream().map(PollOptionResponse::new).toList();
        
        PollResponse pollResponse = new PollResponse(poll, pollOptionResponses);

        PollLog pollLog = pollLogService.getPollLogByUserAndPoll(user, poll);
        if(pollLog != null) {
            pollResponse.setIsJoin(true);
            pollResponse.setVotePollOptionId(pollLog.getPollOption().getPollOptionId());
        }

        return pollResponse;
    }

    public List<PollResponse> getActivePolls(Status status, Integer userId) {
        User user = userService.getUserById(userId);

        List<Poll> polls = pollRepository.findAllByStatus(status);

        return polls.stream().map(poll -> {
                    List<PollOption> pollOptions = pollOptionService.getPollOptionsByPoll(poll);
                    List<PollOptionResponse> pollOptionResponses = pollOptions.stream().map(PollOptionResponse::new).toList();
                    PollResponse pollResponse = new PollResponse(poll, pollOptionResponses);
                    PollLog pollLog = pollLogService.getPollLogByUserAndPoll(user, poll);
                    if(pollLog != null) {
                        pollResponse.setIsJoin(true);
                        pollResponse.setVotePollOptionId(pollLog.getPollOption().getPollOptionId());
                    }
                    return pollResponse;
                }).toList();
    }

    public List<PollResponse> getResultPolls(Status status, Integer userId) {
        User user = userService.getUserById(userId);

        List<Poll> polls = pollRepository.findAllByStatus(status);

        return polls.stream().map(poll -> {
                    PollResponse pollResponse = new PollResponse(poll);
                    PollLog pollLog = pollLogService.getPollLogByUserAndPoll(user, poll);
                    if(pollLog != null) {
                        pollResponse.setIsJoin(true);
                        pollResponse.setVotePollOptionId(pollLog.getPollOption().getPollOptionId());
                    }
                    return pollResponse;
                }).toList();
    }
}
