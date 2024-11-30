package com.swyp.doubleSeven.domain.member.kakao.service;

import java.time.LocalDateTime;

import com.swyp.doubleSeven.domain.member.kakao.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.kakao.dto.reqeust.MemberRequest;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.KakaoUserDTO;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.MemberResponse;
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
        memberRequest.setMemberKeyId(String.valueOf(kakaoUser.getKeyId()));
        memberRequest.setOauthProvider("KAKAO"); // todo: enum에서 가져오는걸로 바꾸기
        memberRequest.setMemberNickname(kakaoUser.getNickname());
        memberRequest.setEmail(kakaoUser.getEmail());
        memberRequest.setRole("MEMBER"); // todo : enum에서 가져오는걸로 바꾸기
        memberRequest.setDltnYn("N");
        memberRequest.setRgstId(0);
        memberRequest.setRgstDt(existingMember == null ? LocalDateTime.now() : existingMember.getRgstDt());
        memberRequest.setUpdtId(0);
        memberRequest.setUpdtDt(LocalDateTime.now());

        memberDAO.upsertMember(memberRequest);
    }
}
