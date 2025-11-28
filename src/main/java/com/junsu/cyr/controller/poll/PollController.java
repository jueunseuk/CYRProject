package com.junsu.cyr.controller.poll;

import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.flow.poll.*;
import com.junsu.cyr.model.poll.PollOptionCount;
import com.junsu.cyr.model.poll.PollResponse;
import com.junsu.cyr.model.poll.PollUpdateRequest;
import com.junsu.cyr.model.poll.PollUploadRequest;
import com.junsu.cyr.service.poll.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poll")
public class PollController {

    private final PollService pollService;
    private final CreatePollFlow createPollFlow;
    private final UpdatePollStatusFlow updatePollStatusFlow;
    private final CastVoteFlow castVoteFlow;
    private final AggregatePollFlow aggregatePollFlow;
    private final PreAggregatePollFlow preAggregatePollFlow;

    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponse> getPoll(@PathVariable("pollId") Integer pollId, @RequestAttribute Integer userId) {
        PollResponse pollResponse = pollService.getPoll(pollId, userId);
        return ResponseEntity.ok(pollResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PollResponse>> getActivePolls(@RequestParam Status status, @RequestAttribute Integer userId) {
        List<PollResponse> pollResponses = pollService.getActivePolls(status, userId);
        return ResponseEntity.ok(pollResponses);
    }

    @GetMapping("/result")
    public ResponseEntity<List<PollResponse>> getResultPolls(@RequestParam Status status, @RequestAttribute Integer userId) {
        List<PollResponse> pollResponses = pollService.getResultPolls(status, userId);
        return ResponseEntity.ok(pollResponses);
    }

    @PostMapping
    public ResponseEntity<String> uploadPoll(@ModelAttribute PollUploadRequest request, @RequestAttribute Integer userId) {
        createPollFlow.createPoll(request, userId);
        return ResponseEntity.ok("success to upload poll");
    }

    @PatchMapping("/{pollId}/status")
    public ResponseEntity<String> updatePollStatus(@RequestBody PollUpdateRequest request, @PathVariable Integer pollId, @RequestAttribute Integer userId) {
        updatePollStatusFlow.updatePollStatus(request, pollId, userId);
        return ResponseEntity.ok("success to update poll state");
    }

    @PostMapping("/{pollId}/{pollOptionId}")
    public ResponseEntity<String> vote(@PathVariable Integer pollId, @PathVariable Long pollOptionId, @RequestAttribute Integer userId) {
        castVoteFlow.castVote(pollId, pollOptionId, userId);
        return ResponseEntity.ok("success to vote");
    }

    @PostMapping("/{pollId}/aggregate")
    public ResponseEntity<List<PollOptionCount>> aggregatePoll(@PathVariable Integer pollId, @RequestAttribute Integer userId) {
        List<PollOptionCount> pollOptionCounts = aggregatePollFlow.aggregatePoll(pollId, userId);
        return ResponseEntity.ok(pollOptionCounts);
    }

    @PostMapping("/{pollId}/aggregate-preview")
    public ResponseEntity<List<PollOptionCount>> aggregatePreviewPoll(@PathVariable Integer pollId, @RequestAttribute Integer userId) {
        List<PollOptionCount> pollOptionCounts = preAggregatePollFlow.preAggregatePoll(pollId, userId);
        return ResponseEntity.ok(pollOptionCounts);
    }
}
