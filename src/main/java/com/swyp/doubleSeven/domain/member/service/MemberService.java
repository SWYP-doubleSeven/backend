package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.KakaoUserDTO;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final KakaoApiClient kakaoApiClient;
    private final MemberDAO memberDAO;

    public String getKakaoAccessToken(String authorizationCode) {
        return kakaoApiClient.requestAccessToken(authorizationCode);
    }

    public MemberResponse processKakaoUser(String accessToken) {
        KakaoUserDTO kakaoUser = kakaoApiClient.getUserInfo(accessToken);
        log.debug("2번 실행");
        MemberResponse existingMember = memberDAO.findMemberByOauthProviderAndMemberId("KAKAO", kakaoUser.getKeyId());
        log.debug("3번 실행");
        MemberRequest.MemberRequestBuilder memberRequestBuilder = MemberRequest.builder()
                .memberKeyId(String.valueOf(kakaoUser.getKeyId()))
                .loginType(LoginType.KAKAO.getType())
                .memberNickname(kakaoUser.getNickname())
                .email(kakaoUser.getEmail())
                .role(Role.MEMBER.getType())
                .dltnYn("N")
                .rgstId(0L)
                .rgstDt(LocalDateTime.now())
                .updtId(0L)
                .updtDt(LocalDateTime.now());

        log.debug("4번 실행");
        if(existingMember == null) {
            // 신규 회원 처리
            memberDAO.insertMember(memberRequestBuilder.build());
            log.debug("5번 실행");
        } else {
            // 기존 회원 처리
            memberRequestBuilder.memberId(existingMember.getMemberId())
                    .updtId("GUEST".equals(existingMember.getLoginType()) ? 0L : existingMember.getMemberId())
                    .memberNickname(existingMember.getMemberNickname())
                    .email(existingMember.getEmail());

            memberDAO.updateMember(memberRequestBuilder.build());
            log.debug("6번 실행");
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
