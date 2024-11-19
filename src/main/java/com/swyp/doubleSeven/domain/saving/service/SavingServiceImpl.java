package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.domain.saving.dao.SavingDAO;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingServiceImpl implements SavingService{

    private final SavingDAO savingDAO;

    // 가상 소비 등록
    @Override
    public SavingResponse createVirtualItem(SavingRequest savingRequest) {
        savingDAO.insertSaving(savingRequest);
        return savingDAO.selectSaving(SavingResponse.builder().build().getSavingId());
    }

    // 가상 소비 단건 조회
    @Override
    public SavingResponse getVirtualItem(Integer savingId, Integer memberId) {
        SavingResponse saving = savingDAO.selectSaving(savingId);
        if (saving == null) {
            throw new RuntimeException("해당 가상소비 내역이 존재하지 않습니다.");
        }
        return saving;
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
