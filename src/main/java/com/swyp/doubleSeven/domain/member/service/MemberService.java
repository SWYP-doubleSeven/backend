package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.common.enums.Role;
import com.swyp.doubleSeven.domain.member.dao.MemberDAO;
import com.swyp.doubleSeven.domain.member.dto.request.MemberRequest;
import com.swyp.doubleSeven.domain.member.dto.response.KakaoUserDTO;
import com.swyp.doubleSeven.domain.member.dto.response.MemberResponse;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final KakaoApiClient kakaoApiClient;
    private final MemberDAO memberDAO;

    public String getKakaoAccessToken(String authorizationCode, HttpServletRequest httpServletRequest) {
        return kakaoApiClient.requestAccessToken(authorizationCode, httpServletRequest);
    }

    public MemberResponse processKakaoUser(String accessToken) {
        KakaoUserDTO kakaoUser = kakaoApiClient.getUserInfo(accessToken);
        MemberResponse existingMember = memberDAO.findMemberByOauthProviderAndMemberId("KAKAO", String.valueOf(kakaoUser.getKeyId()));

        MemberRequest.MemberRequestBuilder memberRequestBuilder = MemberRequest.builder()
                .memberKeyId(String.valueOf(kakaoUser.getKeyId()))
                .loginType(LoginType.KAKAO.getType())
                .memberNickname(kakaoUser.getNickname())
                .email(kakaoUser.getEmail())
                .role(Role.MEMBER.getType())
                .dltnYn("N")
                .rgstId(0)
                .rgstDt(LocalDateTime.now())
                .updtId(0)
                .updtDt(LocalDateTime.now());

        if (existingMember == null) {
            // 신규 회원 처리
            memberDAO.insertMember(memberRequestBuilder.build());
        } else {
            // 기존 회원 처리
            memberRequestBuilder.memberId(existingMember.getMemberId())
                    .updtId("GUEST".equals(existingMember.getLoginType()) ? 0 : existingMember.getMemberId())
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

    /* 랜덤 닉네임 생성 */
    public String createRandomNickname() {
        String[] adjectives = {
                "똑똑한", "성실한", "귀여운", "활기찬", "꼼꼼한", "부지런한", "상냥한", "따뜻한", "웃음 가득한", "소중한",
                "알뜰한", "행복한", "신나는", "찬란한", "순수한", "믿음직한", "기쁜", "사랑스러운", "산뜻한", "평화로운",
                "재치있는", "환한", "멋진", "포근한", "반짝이는", "정직한", "씩씩한", "잔잔한", "당당한", "활발한"
        };
        String[] animals = {
                "강아지", "고양이", "햄스터", "토끼", "앵무새", "거북이", "고슴도치", "다람쥐", "판다", "여우",
                "사슴", "돌고래", "라쿤", "기린", "펭귄", "코알라", "알파카", "너구리", "물개", "부엉이"
        };

        Random random = new Random(); // Random 객체를 한 번만 생성
        String nickname;
        do {
            String adjective = adjectives[random.nextInt(adjectives.length)];
            String animal = animals[random.nextInt(animals.length)];
            String number = String.format("%03d", random.nextInt(1000)); // 0 ~ 999
            nickname = adjective + "_" + animal + "_" + number;
            if(!"true".equals(memberDAO.isSameNickname(nickname))) break;
        } while (true);

        return nickname;
    }
}
