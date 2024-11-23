package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingUpdateRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;

import java.time.LocalDate;
import java.util.List;

public interface SavingService {
    
    // 가상 소비 등록
    SavingResponse createVirtualItem (SavingRequest savingRequest);

    // 가상 소비 조회 (캘린더/리스트)
    //List<SavingListResponse> getVirtualItemList (LocalDate yearMonth, Integer subCategoryId, SortType sortType);
    
    // 가상 소비 단건 조회
    SavingResponse getVirtualItem (Integer savingId, Integer memberId);
    
    // 가상 소비 수정
    void updateVirtualItem(Integer savingId, SavingUpdateRequest savingUpdateRequest);
    
    // 가상 소비 삭제
    int deleteVirtualItem (Integer savingId);
}
