package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.domain.badge.dao.UserBadgeDAO;
import com.swyp.doubleSeven.domain.member.dao.MyPageDAO;
import com.swyp.doubleSeven.domain.member.dto.response.MemberStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageDAO myPageDAO;

    private final UserBadgeDAO userBadgeDAO;

    @Override
    public MemberStatusResponse selectMemberStatus(Integer memberId) {
        MemberStatusResponse memberStatusResponse =  myPageDAO.selectMemberStatus(memberId);
        memberStatusResponse.setBadgeResponseList(userBadgeDAO.getBadgeListLimit3(memberId));
        return memberStatusResponse;
    }
}
