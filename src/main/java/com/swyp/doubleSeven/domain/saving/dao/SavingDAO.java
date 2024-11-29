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
    void insertSaving (@Param("savingRequest") SavingRequest savingRequest);

    // 가상 소비 조회 (월별 => 일자별 합계)
    List<SavingCalendarDayInfoResponse> selectSavingMonthly (int year, int month, String categoryName);

    // 가상 소비 조회 (리스트)
    SavingListResponse selectSavingList (@Param("year") int year,
                                         @Param("month") int month,
                                         @Param("sortType") SortType sortType
    );

    // 가상 소비 단건 조회
    SavingResponse selectSaving (Integer savingId);

    // 가상 소비 수정
    int updateSaving (@Param("savingId") Integer savingId,
                      @Param("savingRequest") SavingRequest savingRequest
    );

    // 가상 소비 삭제
    int deleteSaving (Integer savingId);
}
