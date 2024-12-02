package com.swyp.doubleSeven.domain.member.service;

import com.swyp.doubleSeven.domain.member.dto.response.KakaoMemberResponse;
import com.swyp.doubleSeven.domain.member.dto.response.KakaoTokenResponse;
import com.swyp.doubleSeven.domain.member.dto.response.KakaoUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class KakaoApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public String requestAccessToken(String authorizationCode) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "489a2f33bf9d90c59950291ca077adc9");
        params.add("redirect_uri", "http://localhost:8090/api/auth/kakao-login");
//        params.add("redirect_uri", "http://3.39.123.15:8090/api/auth/kakao-login");
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(url, request, KakaoTokenResponse.class);
            return response.getBody().getAccessToken();
        } catch(Exception e) {
            log.error("카카오 api호출 실패 : ", e);
            throw new RuntimeException("카카오api호출 실패", e);
        }

    }

    public KakaoUserDTO getUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoMemberResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, KakaoMemberResponse.class);

        KakaoMemberResponse memberResponse = response.getBody();
        return new KakaoUserDTO(
                memberResponse.getId(), // keyId
                memberResponse.getProperties().get("nickname"),
                memberResponse.getKakaoAccount().getEmail()
        );
    }
}

