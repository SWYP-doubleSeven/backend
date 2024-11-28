package com.swyp.doubleSeven.domain.member;

import java.time.LocalDateTime;


public class Member {

    private Integer memberId;          // 사용자 ID
    private String password;           // 사용자 비밀번호 (추가)
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

    //기본 생성자
    public Member() {
        this.rgstDt = LocalDateTime.now();

    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getDltnYn() {
        return dltnYn;
    }

    public void setDltnYn(String dltnYn) {
        this.dltnYn = dltnYn;
    }

    public Integer getRgstId() {
        return rgstId;
    }

    public void setRgstId(Integer rgstId) {
        this.rgstId = rgstId;
    }

    public LocalDateTime getRgstDt() {
        return rgstDt;
    }

    public void setRgstDt(LocalDateTime rgstDt) {
        this.rgstDt = rgstDt;
    }

    public Integer getUpdtId() {
        return updtId;
    }

    public void setUpdtId(Integer updtId) {
        this.updtId = updtId;
    }

    public LocalDateTime getUpdtDt() {
        return updtDt;
    }

    public void setUpdtDt(LocalDateTime updtDt) {
        this.updtDt = updtDt;
    }



}
