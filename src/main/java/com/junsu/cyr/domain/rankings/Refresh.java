package com.junsu.cyr.domain.rankings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Refresh {

    MIDNIGHT("매일 자정", "0 0 0 * * *"),
    HOURLY("1시간마다", "0 0 * * * *"),
    THREE_HOURLY("3시간마다", "0 0 */3 * * *"),
    TEN_MINUTES("10분마다", "0 */10 * * * *");

    private final String description;
    private final String cron;
}