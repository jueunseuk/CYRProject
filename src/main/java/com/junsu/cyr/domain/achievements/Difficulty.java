package com.junsu.cyr.domain.achievements;

import lombok.Getter;

@Getter
public enum Difficulty {
    BEGINNER(1),
    EASY(2),
    NORMAL(3),
    HARD(4),
    EXTREME(5);

    private final int score;

    Difficulty(Integer score) {
        this.score = score;
    }
}
