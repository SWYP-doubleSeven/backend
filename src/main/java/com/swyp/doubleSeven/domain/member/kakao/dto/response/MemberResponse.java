package com.swyp.doubleSeven.domain.member.kakao.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    private Long memberId;
    private String memberKeyId;
    private String memberNickname;
    private String memberName;
    private String email;
    private String role;
    private String loginType;
    private String dltnYn;
    private LocalDateTime rgstDt;
    private LocalDateTime updtDt;
}
