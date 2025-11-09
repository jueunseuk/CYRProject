package com.junsu.cyr.controller.ranking;

import com.junsu.cyr.domain.rankings.Period;
import com.junsu.cyr.domain.rankings.Type;
import com.junsu.cyr.model.ranking.RankingConditionRequest;
import com.junsu.cyr.model.ranking.RankingResponse;
import com.junsu.cyr.service.ranking.RankingAggregationService;
import com.junsu.cyr.service.ranking.RankingService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingAggregationService rankingAggregationService;
    private final RankingService rankingService;
    private final UserService userService;

    @PatchMapping("/refresh")
    public ResponseEntity<String> refreshRankingForce(@RequestParam Type type, @RequestParam Period period, @RequestAttribute Integer userId) {
        rankingAggregationService.refreshRanking(type, period, userId);
        return ResponseEntity.ok("success to refresh ranking");
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<RankingResponse>> getRanking(@PathVariable Type type, @ModelAttribute RankingConditionRequest condition, @RequestAttribute Integer userId) {
        userService.getUserById(userId);
        List<RankingResponse> rankingResponses = rankingService.getRanking(type, condition);
        return ResponseEntity.ok(rankingResponses);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<RankingResponse>> getSummaryRanking() {
        List<RankingResponse> rankingResponses = rankingService.getSummaryRanking();
        return ResponseEntity.ok(rankingResponses);
    }
}
