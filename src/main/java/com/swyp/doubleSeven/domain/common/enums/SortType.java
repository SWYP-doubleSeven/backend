package com.swyp.doubleSeven.domain.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
    LATEST("latest"),
    OLDEST("oldest"),
    AMOUNT_DESC("amount-desc"),
    AMOUNT_ASC("amount-asc");

    private final String value;

    public static SortType fromString(String text) {
        for (SortType type : SortType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return LATEST; // 기본값
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}