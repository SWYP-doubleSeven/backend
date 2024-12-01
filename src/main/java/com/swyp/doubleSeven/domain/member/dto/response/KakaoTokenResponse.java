package com.swyp.doubleSeven.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
