package com.swyp.doubleSeven.domain.member.dao;

import com.swyp.doubleSeven.domain.member.dto.response.MemberStatusResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageDAO {

    // 가입일로부터 얼마를 아꼈는지
    MemberStatusResponse selectMemberStatus (Integer memberId);
}
