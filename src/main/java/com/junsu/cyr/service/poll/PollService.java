package com.junsu.cyr.service.poll;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollLog;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.*;
import com.junsu.cyr.repository.PollRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.service.image.S3Service;
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
    private final S3Service s3Service;
    private final PollLogService pollLogService;

    public Poll getPollByPollId(Integer pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new BaseException(PollExceptionCode.NOT_FOUND_POLL));
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

    @Transactional
    public void uploadPoll(PollUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        if(request.getOptions() == null || request.getOptions().size() < 2) {
            throw new BaseException(PollExceptionCode.INSUFFICIENT_TO_OPTIONS);
        }

        if(request.getTitle().isEmpty() || request.getDescription().isEmpty()) {
            throw new BaseException(PollExceptionCode.INSUFFICIENT_TO_DESCRIBE);
        }

        if(request.getClosedAt().isBefore(LocalDateTime.now())) {
            throw new BaseException(PollExceptionCode.INVALID_CLOSED_AT);
        }

        String imageUrl = null;
        try {
            if(request.getFile() != null) {
                imageUrl = s3Service.uploadFile(request.getFile(), Type.POLL);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        Poll poll = Poll.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.IN_PROGRESS)
                .imageUrl(imageUrl)
                .closedAt(request.getClosedAt())
                .build();

        pollRepository.save(poll);

        pollOptionService.createPollOptions(poll, request.getOptions());
    }

    @Transactional
    public void updatePoll(PollUpdateRequest request, Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = getPollByPollId(pollId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(PollExceptionCode.NOT_ALLOWED_TO_MAKE_POLL);
        }

        if(request.getTitle().isEmpty() || request.getDescription().isEmpty()) {
            throw new BaseException(PollExceptionCode.INSUFFICIENT_TO_DESCRIBE);
        }

        if(request.getClosedAt().isBefore(LocalDateTime.now())) {
            throw new BaseException(PollExceptionCode.INVALID_CLOSED_AT);
        }

        poll.update(request);
    }

    @Transactional
    public void vote(Integer pollId, Long pollOptionId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = getPollByPollId(pollId);
        PollOption pollOption = pollOptionService.getPollOptionBYPollOptionId(pollOptionId);

        if(pollLogService.checkPollLogByUserAndPoll(user, poll)) {
            throw new BaseException(PollExceptionCode.ALREADY_PARTICIPATING_VOTE);
        }

        if(!pollOption.getPoll().equals(poll)) {
            throw new BaseException(PollExceptionCode.POLL_AND_OPTION_MISMATCH);
        }

        userService.addExpAndSand(user, 6, 14);

        pollLogService.createPollLog(user, poll, pollOption);
    }

    @Transactional
    public List<PollOptionCount> aggregatePoll(Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = getPollByPollId(pollId);

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

        poll.updateResult(max);

        pollLogs.sort((o1, o2) -> {
            if (o1.getVoteCount().equals(o2.getVoteCount())) {
                return Long.compare(o1.getPollOptionId(), o2.getPollOptionId());
            }
            return Long.compare(o2.getVoteCount(), o1.getVoteCount());
        });

        return pollLogs;
    }

    public List<PollOptionCount> aggregatePreviewPoll(Integer pollId, Integer userId) {
        User user = userService.getUserById(userId);
        Poll poll = getPollByPollId(pollId);

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
