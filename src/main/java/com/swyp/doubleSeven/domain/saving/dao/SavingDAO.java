package com.swyp.doubleSeven.domain.saving.dao;

import com.swyp.doubleSeven.domain.common.enums.SortType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingCalendarDayInfoResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingListResponse;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavingDAO {

    // 가상 소비 등록
    int insertSaving (SavingRequest savingRequest);

    // 가상 소비 조회 (월별 => 일자별 합계)
    List<SavingCalendarDayInfoResponse> selectSavingMonthly (int year, int month, String categoryName, Integer memberId);

    // 가상 소비 조회 (리스트)
    SavingListResponse selectSavingList (@Param("year") int year, @Param("month") int month, Integer memberId);

    // 가상 소비 단건 조회
    SavingResponse selectSaving (Integer savingId, Integer memberId);

    // 가상 소비 수정
    int updateSaving (@Param("savingId") Integer savingId,
                      @Param("savingRequest") SavingRequest savingRequest
    );

    // 가상 소비 삭제 (소프트 삭제)
    int deleteSaving (Integer savingId, Integer memberId);

    // 가상 소비 복구 (데이터 복구)
    int cancleSavingDelete (Integer savingId, Integer memberId);

}
