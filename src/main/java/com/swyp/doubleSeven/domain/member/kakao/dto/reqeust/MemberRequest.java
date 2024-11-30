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
    private String loginType;
    private String dltnYn;
    private Long rgstId;
    private LocalDateTime rgstDt;
    private Long updtId;
    private LocalDateTime updtDt;
}

