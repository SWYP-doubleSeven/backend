package com.swyp.doubleSeven.domain.member.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swyp.doubleSeven.domain.member.Member;
import com.swyp.doubleSeven.domain.member.login.socialLogin.GoogleOauth;
import com.swyp.doubleSeven.domain.member.login.socialType.SocialLoginType;
import com.swyp.doubleSeven.domain.member.dao.MemberDao;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService { //필드에 MemberService 받아서 Mybatis xml 접근

    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    MemberDao memberMapper;

    @Override
    public Member login(Member member) {
        return null;
    }

    public void request(SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
//        switch (socialLoginType){
//            case GOOGLE:{
//                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.프로세스이다
        redirectURL = googleOauth.getOauthRedirectUrl();
//            }break;
//            default:{
//                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
//            }

        response.sendRedirect(redirectURL);
    }

    public void oauthLogin(SocialLoginType socialLoginType, String code) throws IOException {
        if (socialLoginType == SocialLoginType.GOOGLE) {
            try {
                // 액세스 토큰 요청
                String accessToken = googleOauth.requestAccessToken(code);
                // 사용자 정보 가져오기
                Map<String, Object> userInfo = googleOauth.getUserInfo(accessToken);
                // 사용자 정보 처리 (회원가입 또는 로그인)
                handleUserInfo(userInfo);
                // 로그인 성공 후 리다이렉트 또는 토큰 발급 등
                response.sendRedirect("/home"); // 예시로 홈 페이지로 리다이렉트
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException("구글 로그인 처리 중 오류 발생");
            }
        } else {
            throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
        }
    }

    private void handleUserInfo(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        // 데이터베이스에서 사용자 조회
        Member member = memberMapper.selectMemberByEmail(email);

        if (member == null) {
            // 신규 회원 가입
            member = new Member();
            member.setEmail(email);
            member.setMemberName(name);
            member.setMemberNickname(name);
            member.setRole("ROLE_USER");
            member.setOauthProvider("G"); // 구글의 경우 'G'
            member.setDltnYn("N");
            member.setRgstId(0); // 시스템 관리자 ID로 설정하거나 적절한 값으로 설정
            member.setUpdtId(0);

            memberMapper.insertMember(member);
        } else {
            // 기존 회원 로그인 처리
            // 필요에 따라 업데이트 로직 추가
        }

        // 세션 또는 토큰을 통해 로그인 상태 유지
        // 예를 들어, 세션에 사용자 정보 저장
        // HttpSession session = request.getSession();
        // session.setAttribute("member", member);
    }


}


