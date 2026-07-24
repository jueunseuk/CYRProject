package com.junsu.cyr.domain.songs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CreatorRole {

    LYRICS("작사"),
    COMPOSITION("작곡"),
    ARRANGEMENT("편곡"),

    VOCAL("보컬"),
    CHORUS("코러스"),
    INSTRUMENT("악기 연주"),
    MIDI_PROGRAMMING("MIDI 프로그래밍"),
    SOUND_EFFECTS("사운드 이펙트"),

    STRING_ARRANGEMENT("스트링 편곡"),
    CONDUCTING("지휘"),

    RECORDING("레코딩"),
    MIXING("믹싱"),
    MASTERING("마스터링"),

    PRODUCER("프로듀서"),
    EXECUTIVE_PRODUCER("총괄 프로듀서"),
    A_AND_R("A&R"),
    MANAGEMENT("매니지먼트"),
    DESIGN("디자인"),
    CONTENTS("콘텐츠");

    private final String description;
}