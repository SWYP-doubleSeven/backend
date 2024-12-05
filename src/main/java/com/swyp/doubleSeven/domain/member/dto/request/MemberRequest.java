package com.swyp.doubleSeven.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {
    private Integer memberId;
    private String memberKeyId;
    private String memberNickname;
    private String memberName;
    private String email;
    private String role;
    private String loginType;
    private String dltnYn;
    private Integer rgstId;
    private LocalDateTime rgstDt;
    private Integer updtId;
    private LocalDateTime updtDt;
}

