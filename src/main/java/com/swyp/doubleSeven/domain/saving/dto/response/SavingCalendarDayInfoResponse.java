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
public class SavingCalendarDayInfoResponse {

    private int day;

    private long totalAmount;

    private List<SavingListResponse> items;
}
