package com.swyp.doubleSeven.domain.saving.dao;

import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SavingDAO {

    // 가상 소비 등록
    void insertSaving (SavingRequest savingRequest);

    // 가상 소비 단건 조회
    SavingResponse selectSaving (Integer savingId);

    // 가상 소비 삭제
    int deleteSaving (Integer savingId);
}
