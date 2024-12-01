package com.swyp.doubleSeven.domain.member.dao;

import com.swyp.doubleSeven.domain.member.dto.reqeust.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
    MemberResponse findMemberByOauthProviderAndMemberId(@Param("loginType") String loginType, @Param("memberKeyId") Long memberKeyId);
    void insertMember(MemberRequest memberRequest);
    void updateMember(MemberRequest memberRequest);
}
