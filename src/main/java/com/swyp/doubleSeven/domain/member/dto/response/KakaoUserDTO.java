package com.swyp.doubleSeven.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserDTO {
    private Long keyId;
    private String nickname;
    private String email;
}
