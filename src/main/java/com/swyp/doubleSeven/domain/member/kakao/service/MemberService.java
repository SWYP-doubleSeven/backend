package com.swyp.doubleSeven.domain.member.kakao.service;

import java.time.LocalDateTime;

import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.kakao.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.kakao.dto.reqeust.MemberRequest;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.KakaoUserDTO;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.member.login.socialType.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final KakaoApiClient kakaoApiClient;
    private final MemberDAO memberDAO;

    public String getKakaoAccessToken(String authorizationCode) {
        return kakaoApiClient.requestAccessToken(authorizationCode);
    }

    public void processKakaoUser(String accessToken) {
        KakaoUserDTO kakaoUser = kakaoApiClient.getUserInfo(accessToken);

        MemberResponse existingMember = memberDAO.findMemberByOauthProviderAndMemberId("KAKAO", kakaoUser.getKeyId());

        MemberRequest memberRequest = new MemberRequest();
        if(existingMember == null) {
            memberRequest.setMemberKeyId(String.valueOf(kakaoUser.getKeyId()));
            memberRequest.setLoginType(String.valueOf(SocialLoginType.KAKAO.getType()));
            memberRequest.setMemberNickname(kakaoUser.getNickname());
            memberRequest.setEmail(kakaoUser.getEmail());
            memberRequest.setRole(Role.MEMBER.getType());
            memberRequest.setDltnYn("N");
            memberRequest.setRgstId(0L);
            memberRequest.setRgstDt(existingMember == null ? LocalDateTime.now() : existingMember.getRgstDt());
            memberRequest.setUpdtId(0L);
            memberRequest.setUpdtDt(LocalDateTime.now());

            memberDAO.insertMember(memberRequest);
        } else {
            // todo : 마지막 로그인일시 새 테이블에 기록
            memberRequest.setMemberId(existingMember.getMemberId());
            memberRequest.setRole(Role.MEMBER.getType());
            memberRequest.setLoginType(String.valueOf(SocialLoginType.KAKAO.getType()));
            memberRequest.setUpdtDt(LocalDateTime.now());

            if ("GUEST".equals(existingMember.getLoginType())) { // 게스트->카카오로그인
                memberRequest.setUpdtId(0L);
            } else {
                memberRequest.setUpdtId(existingMember.getMemberId());
            }

            memberDAO.updateMember(memberRequest);
        }




    }
}
