package com.swyp.doubleSeven.domain.common.enums;

import lombok.Getter;

@Getter
public enum BadgeType {

    ATTENDANCE("ATTENDANCE", "출석"),
    LOG("LOG", "기록"),
    MONEY("MONEY", "저축"),
    DATE("날짜", "출석"),
    CONSECUTIVE_ATTENDANCE("CONSECUTIVE_ATTENDANCE", "연속출석"),
    MAX_MONEY("MAX_MONEY", "연속출석");

    private final String name;
    private final String description;

    BadgeType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
