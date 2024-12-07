package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.member.dto.response.MemberStatusResponse;

public interface MyPageService {

    // 가입일로부터 얼마를 아꼈는지
    MemberStatusResponse selectMemberStatus (Integer memberId);
}
