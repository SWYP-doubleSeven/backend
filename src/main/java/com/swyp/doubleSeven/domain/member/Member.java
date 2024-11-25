package com.swyp.doubleSeven.domain.member;

import java.time.LocalDateTime;

public class Member {
    private Integer memberId;          // 사용자 ID
    private String memberNickname;     // 사용자 닉네임
    private String memberName;         // 사용자 이름
    private String email;              // 사용자 이메일
    private String role;               // 권한
    private String oauthProvider;      // 소셜 로그인 제공자
    private String dltnYn;             // 탈퇴 여부
    private Integer rgstId;            // 등록자 ID
    private LocalDateTime rgstDt;      // 등록 일시
    private Integer updtId;            // 수정자 ID
    private LocalDateTime updtDt;      // 수정 일시


}
