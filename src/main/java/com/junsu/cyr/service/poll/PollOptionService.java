package com.junsu.cyr.service.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.repository.PollOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollOptionService {

    private final PollOptionRepository pollOptionRepository;

    public List<PollOption> getPollOptionsByPoll(Poll poll) {
        return pollOptionRepository.findAllByPoll(poll);
    }

    @Transactional
    public void createPollOptions(Poll poll, List<String> options) {
        List<PollOption> pollOptions = options.stream()
                .map(option -> PollOption.builder()
                        .poll(poll)
                        .content(option)
                        .build())
                .toList();

        pollOptionRepository.saveAll(pollOptions);
    }

    public PollOption getPollOptionBYPollOptionId(Long pollOptionId) {
        return pollOptionRepository.findByPollOptionId(pollOptionId);
    }
}
