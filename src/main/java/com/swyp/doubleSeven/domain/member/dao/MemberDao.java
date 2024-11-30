package com.swyp.doubleSeven.domain.member.dao;

import com.swyp.doubleSeven.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    Member selectMemberByEmail(String email);

    void insertMember(Member member);

    void updateMember(Member member);

//    Member findByUsername(String username);
//
//    Member findByEmail(String email);
//
//    Member findByPhone(String phone);




}
