package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;

import java.util.List;

public interface SavingService {
    
    // 가상 소비 등록
    void createVirtualItem (SavingRequest savingRequest);

    // 가상 소비 조회 (월별 => 일자별 합계)
    SavingCalendarResponse getVirtualItemMonthly (int year, int month, String categoryName, Integer memberId);

    // 가상 소비 조회 (리스트)
    SavingListResponse getVirtualItemList (int year, int month, SortType sortType, Integer memberId);

    // 가상 소비 조회 (캘린더/리스트)
    //List<SavingListResponse> getVirtualItemList (LocalDate yearMonth, Integer subCategoryId, SortType sortType);
    
    // 가상 소비 단건 조회
    SavingResponse getVirtualItem (Integer savingId, Integer memberId);
    
    // 가상 소비 수정
    void updateVirtualItem (Integer savingId, SavingRequest savingRequest);
    
    // 가상 소비 삭제
    int deleteVirtualItem (Integer savingId, Integer memberId);
}
