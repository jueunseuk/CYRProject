package com.junsu.cyr.service.cheer;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.cheers.CheerSummaryId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CheerSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CheerSummaryService {

    private final CheerSummaryRepository cheerSummaryRepository;

    @Transactional
    public CheerSummary createCheerSummary(User user) {
        CheerSummaryId cheerSummaryId = new CheerSummaryId(user.getUserId(), LocalDate.now());
        CheerSummary cheerSummary = CheerSummary.builder()
                .cheerSummaryId(cheerSummaryId)
                .count(0L)
                .updatedAt(null)
                .build();

        return cheerSummaryRepository.save(cheerSummary);
    }
}
