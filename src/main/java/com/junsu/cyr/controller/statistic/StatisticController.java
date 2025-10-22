package com.junsu.cyr.controller.statistic;

import com.junsu.cyr.domain.statistics.Statistic;
import com.junsu.cyr.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<Statistic> getStatistic() {
        Statistic statistic = statisticService.getStatistic();
        return ResponseEntity.ok(statistic);
    }
}
