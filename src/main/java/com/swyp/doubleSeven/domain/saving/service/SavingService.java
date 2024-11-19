package com.swyp.doubleSeven.domain.saving.service;

import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;

public interface SavingService {
    SavingResponse createVirtualItem (SavingRequest savingRequest);
    SavingResponse getVirtualItem (Integer savingId, Integer memberId);
    int deleteVirtualItem (Integer savingId);

}
