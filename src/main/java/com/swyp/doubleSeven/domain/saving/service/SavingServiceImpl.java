package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.common.aspect.AuthenticationAspect;
import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.Error;
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
    public SavingResponse createVirtualItem (SavingRequest savingRequest) {
        int result = savingDAO.insertSaving(savingRequest);
        if(result >0) {
            List<BadgeResponse> badgeResponseList = commonAspect.afterSaving(savingRequest);
        }

        // Insert 성공 후 로그로 확인
        log.info("Insert 후 생성된 savingId: {}", savingRequest.getSavingId());

        return SavingResponse.builder()
                .memberId(savingRequest.getMemberId())
                .savingId(savingRequest.getSavingId())
                .savingYmd(savingRequest.getSavingYmd())
                .amount(savingRequest.getAmount())
                .categoryName(savingRequest.getCategoryName())
                .build();
    }

    // 가상 소비 조회 (월별 => 일자별 합계)
    @Override
    public SavingCalendarResponse getVirtualItemMonthly (int year, int month, String categoryName, Integer memberId) {
        List<SavingCalendarDayInfoResponse> days = savingDAO.selectSavingMonthly(year, month, categoryName, memberId);

        return SavingCalendarResponse.builder()
                .year(year)
                .month(month)
                .days(days)
                .build();
    }

    // 가상 소비 조회 (리스트)
    @Override
    public SavingListResponse getVirtualItemList(int year, int month, SortType sortType, Integer memberId) {
        return savingDAO.selectSavingList(year, month, sortType, memberId);
    }


    // 가상 소비 단건 조회
    @Override
    public SavingResponse getVirtualItem (Integer savingId, Integer memberId) {
        SavingResponse virtualItemResult = savingDAO.selectSaving(savingId, memberId);
        if (virtualItemResult == null) {
            throw new BusinessException(Error.BAD_REQUEST);
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
    public int deleteVirtualItem (Integer savingId, Integer memberId) {
        int result = savingDAO.deleteSaving(savingId, memberId);

        if (result == 0) {
            throw new RuntimeException("삭제할 가상소비 내역이 존재하지 않습니다.");
        }

        return result;
    }

    public static SavingRequest setSaving (SavingRequest request, Integer memberId) {
        return SavingRequest.builder()
                .memberId(memberId)
                .savingYmd(request.getSavingYmd())
                .categoryName(request.getCategoryName())
                .amount(request.getAmount())
                .build();
    }
}
