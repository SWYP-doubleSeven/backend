package com.swyp.doubleSeven.domain.member.kakao.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    private Long memberId;
    private String memberKeyId;
    private String memberNickname;
    private String memberName;
    private String email;
    private String role;
    private String oauthProvider;
    private String dltnYn;
    private int rgstId;
    private LocalDateTime rgstDt;
    private int updtId;
    private LocalDateTime updtDt;
}

