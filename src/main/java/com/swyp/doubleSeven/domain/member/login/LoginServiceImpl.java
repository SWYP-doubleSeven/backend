package com.swyp.doubleSeven.domain.member.login;

import com.swyp.doubleSeven.domain.member.Member;
import com.swyp.doubleSeven.domain.member.service.MemberService;

public class LoginServiceImpl implements LoginService{ //필드에 MemberService 받아서 Mybatis xml 접근

    private MemberService memberService;


    @Override
    public Member login(Member member) {
        return null;
    }
}
