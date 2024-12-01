package com.swyp.doubleSeven.domain.member.guest.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpiredGuestRequest {

    private Integer memberId;

    private String memberKeyId;
}
