package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.common.aspect.AuthenticationUtil;
import com.swyp.doubleSeven.domain.member.dao.MyPageDAO;
import com.swyp.doubleSeven.domain.member.dto.response.MemberStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageDAO myPageDAO;

    @Override
    public MemberStatusResponse selectMemberStatus(Integer memberId) {
        return myPageDAO.selectMemberStatus(memberId);
    }
}
