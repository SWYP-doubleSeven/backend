package com.swyp.doubleSeven.domain.member.dao;

import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
    MemberResponse findMemberByMemberKeyId(String memberKeyId);
    MemberResponse findMemberByMemberId(Integer memberId);
    void insertMember(MemberRequest memberRequest);
    void updateMember(MemberRequest memberRequest);

    String isSameNickname(String nickname);

    int updateMemberInfo(MemberRequest memberRequest);

    int deleteOldMember();
}
