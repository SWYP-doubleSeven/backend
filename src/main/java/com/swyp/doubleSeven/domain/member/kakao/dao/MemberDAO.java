package com.swyp.doubleSeven.domain.member.kakao.dao;

import com.swyp.doubleSeven.domain.member.kakao.dto.reqeust.MemberRequest;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.MemberResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
    MemberResponse findMemberByOauthProviderAndMemberId(@Param("oauthProvider") String oauthProvider, @Param("memberKeyId") Long memberKeyId);
    void upsertMember(MemberRequest memberRequest);
}
