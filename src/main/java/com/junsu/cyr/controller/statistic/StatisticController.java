package com.junsu.cyr.controller.statistic;

import com.junsu.cyr.domain.statistics.Statistic;
import com.junsu.cyr.service.statistic.StatisticService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService statisticService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Statistic> getStatistic() {
        Statistic statistic = statisticService.getStatistic();
        return ResponseEntity.ok(statistic);
    }

    @PostMapping
    public ResponseEntity<String> addStatistic() {
        statisticService.createStatistic();
        return ResponseEntity.ok("success to make statistic");
    }
}
