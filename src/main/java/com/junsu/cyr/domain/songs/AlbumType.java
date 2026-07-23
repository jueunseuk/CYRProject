package com.junsu.cyr.domain.songs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlbumType {
    FULL_LENGTH("정규"),
    EP("미니"),
    SINGLE("싱글"),
    DIGITAL_SINGLE("디지털 싱글"),
    OST("OST"),
    LIVE("라이브"),
    COMPILATION("컴필레이션"),
    REMAKE("리메이크"),
    ETC("기타");

    private final String description;
}
