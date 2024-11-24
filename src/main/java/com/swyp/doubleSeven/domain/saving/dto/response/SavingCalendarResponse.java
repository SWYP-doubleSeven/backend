package com.swyp.doubleSeven.domain.saving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingCalendarResponse {

    private int year;

    private int month;

    private List<SavingCalendarDayInfoResponse> days;
}
