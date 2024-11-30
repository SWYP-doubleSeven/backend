package com.swyp.doubleSeven.domain.member.kakao.service;

import java.time.LocalDateTime;

import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.kakao.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.kakao.dto.reqeust.MemberRequest;
import com.swyp.doubleSeven.domain.member.kakao.dto.response.KakaoMemberResponse;
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

    public MemberResponse processKakaoUser(String accessToken) {
        KakaoUserDTO kakaoUser = kakaoApiClient.getUserInfo(accessToken);

        MemberResponse existingMember = memberDAO.findMemberByOauthProviderAndMemberId("KAKAO", kakaoUser.getKeyId());

        MemberRequest.MemberRequestBuilder memberRequestBuilder = MemberRequest.builder()
                .memberKeyId(String.valueOf(kakaoUser.getKeyId()))
                .loginType(SocialLoginType.KAKAO.getType())
                .memberNickname(kakaoUser.getNickname())
                .email(kakaoUser.getEmail())
                .role(Role.MEMBER.getType())
                .dltnYn("N")
                .rgstId(0L)
                .rgstDt(LocalDateTime.now())
                .updtId(0L)
                .updtDt(LocalDateTime.now());

        if(existingMember == null) {
            // 신규 회원 처리
            memberDAO.insertMember(memberRequestBuilder.build());
        } else {
            // 기존 회원 처리
            memberRequestBuilder.memberId(existingMember.getMemberId())
                    .updtId("GUEST".equals(existingMember.getLoginType()) ? 0L : existingMember.getMemberId())
                    .memberNickname(existingMember.getMemberNickname())
                    .email(existingMember.getEmail());

            memberDAO.updateMember(memberRequestBuilder.build());
        }

        // MemberResponse 생성
        return MemberResponse.builder()
                .memberId(memberRequestBuilder.memberKeyId(String.valueOf(kakaoUser.getKeyId())).build().getMemberId())
                .memberNickname(memberRequestBuilder.build().getMemberNickname())
                .email(memberRequestBuilder.build().getEmail())
                .role(memberRequestBuilder.build().getRole())
                .loginType(memberRequestBuilder.build().getLoginType())
                .build();
    }
}
