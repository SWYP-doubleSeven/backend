package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.common.util.CommonAspect;
import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dao.SavingDAO;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarDayInfoResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingServiceImpl implements SavingService{

    private final SavingDAO savingDAO;
    private final CommonAspect commonAspect;

    // 가상 소비 등록
    @Override
    public int createVirtualItem (SavingRequest savingRequest) {
        int result = savingDAO.insertSaving(savingRequest);
        if(result >0) {
            commonAspect.afterSaving(savingRequest);
        }

        return result;
    }

    // 가상 소비 조회 (월별 => 일자별 합계)
    @Override
    public SavingCalendarResponse getVirtualItemMonthly (int year, int month, String categoryName) {
        List<SavingCalendarDayInfoResponse> days = savingDAO.selectSavingMonthly(year, month, categoryName);

        return SavingCalendarResponse.builder()
                .year(year)
                .month(month)
                .days(days)
                .build();
    }

    // 가상 소비 조회 (리스트)
    @Override
    public SavingListResponse getVirtualItemList(int year, int month, SortType sortType) {
        return savingDAO.selectSavingList(year, month, sortType);
    }


    // 가상 소비 단건 조회
    @Override
    public SavingResponse getVirtualItem (Integer savingId) {
        SavingResponse virtualItemResult = savingDAO.selectSaving(savingId);
        if (virtualItemResult == null) {
            throw new RuntimeException("해당 가상소비 내역이 존재하지 않습니다.");
        }
        return virtualItemResult;
    }

    @Override
    public void updateVirtualItem(Integer savingId, SavingRequest savingRequest) {
        int result = savingDAO.updateSaving(savingId, savingRequest);
        if (result == 0) {
            throw new RuntimeException("수정할 가상소비 내역이 존재하지 않습니다.");
        }
    }

    // 가상 소비 삭제
    @Override
    public int deleteVirtualItem (Integer savingId) {
        int result = savingDAO.deleteSaving(savingId);

        if (result == 0) {
            throw new RuntimeException("삭제할 가상소비 내역이 존재하지 않습니다.");
        }

        return result;
    }
}
