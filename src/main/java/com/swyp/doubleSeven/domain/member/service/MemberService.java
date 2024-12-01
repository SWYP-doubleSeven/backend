package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberService {

    Member selectMemberByEmail(String email);

    void insertMember(Member member);

    void updateMember(Member member);

    Member findByUsername(String username);

    Member findByEmail(String email);

    Member findByPhone(String phone);




}
