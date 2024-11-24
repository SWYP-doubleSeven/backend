package com.swyp.doubleSeven.domain.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SortType {
    LATEST("latest", "최신순", "s.SAVING_YMD DESC"),
    OLDEST("oldest", "오래된순", "s.SAVING_YMD ASC"),
    AMOUNT_DESC("amount-desc", "금액 높은순", "s.AMOUNT DESC, s.SAVING_YMD DESC"),
    AMOUNT_ASC("amount-asc", "금액 낮은순", "s.AMOUNT ASC, s.SAVING_YMD DESC");

    private final String code;
    private final String description;
    private final String sqlOrderBy;

    public static SortType fromCode(String code) {
        return Arrays.stream(SortType.values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(LATEST);
    }
}