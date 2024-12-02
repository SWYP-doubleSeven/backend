package com.swyp.doubleSeven.domain.member.dto.request.guest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpiredGuestRequest {

    private Integer memberId;

    private String memberKeyId;
}
