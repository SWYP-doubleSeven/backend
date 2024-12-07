package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.common.category.dao.CategoryDAO;
import com.swyp.doubleSeven.domain.common.category.error.CategoryError;
import com.swyp.doubleSeven.domain.common.category.service.CategoryService;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingServiceImpl implements SavingService{

    private final SavingDAO savingDAO;

    private final CommonAspect commonAspect;

    private final CategoryDAO categoryDAO;

    // 가상 소비 등록
    @Override
    @Transactional
    public SavingResponse createVirtualItem (SavingRequest savingRequest) {
        int result = savingDAO.insertSaving(savingRequest);

        if(result >0) {
            incrementCategoryCount(savingRequest);
            commonAspect.afterSaving(savingRequest);
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
    public SavingListResponse getVirtualItemList(int year, int month, Integer memberId) {
        return savingDAO.selectSavingList(year, month, memberId);
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

    // 가상 소비 삭제 (소프트 삭제)
    @Override
    public int deleteVirtualItem (Integer savingId, Integer memberId) {
        int result = savingDAO.deleteSaving(savingId, memberId);

        if (result == 0) {
            throw new RuntimeException("삭제할 가상소비 내역이 존재하지 않습니다.");
        }

        return result;
    }


    // 가상 소비 복구 (데이터 복구)
    @Override
    public int cancleSavingDelete (Integer savingId, Integer memberId) {
        int result = savingDAO.cancleSavingDelete(savingId, memberId);

        if (result == 0) {
            throw new BusinessException(Error.BAD_REQUEST);
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

    // 많이 사용한 순으로 카테고리 정렬
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int incrementCategoryCount(SavingRequest savingRequest) {
        int result = categoryDAO.incrementCategoryCount(savingRequest.getMemberId(), savingRequest.getCategoryName());
        log.info("incrementCategoryCount result: {}, {}", savingRequest.getMemberId(), savingRequest.getCategoryName());
        if (result == 0) {
            throw new BusinessException(CategoryError.CATEGORY_COUNT_FAILED);
        }

        return result;
    }
}
