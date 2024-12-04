package com.swyp.doubleSeven.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GoogleOauth {

    //applications.yml 에서 value annotation을 통해서 값을 받아오기
    @Value("${spring.OAuth2.google.url}")
    private String GOOGLE_SNS_LOGIN_URL;

    @Value("${spring.OAuth2.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${spring.OAuth2.google.callback-url}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${spring.OAuth2.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${spring.OAuth2.google.scope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GOOGLE_SNS_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";      // 좀더 알아보고 해당 정보값 수정해야 할수도 있음
    private static final String GOOGLE_SNS_USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public String getOauthRedirectUrl() {
        Map<String,Object> params=new HashMap<>();
        params.put("scope",GOOGLE_DATA_ACCESS_SCOPE);
        params.put("response_type","code");
        params.put("client_id",GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri",GOOGLE_SNS_CALLBACK_URL);

        //parameter를 형식에 맞춰 구성해주는 함수
        String parameterString=params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL=GOOGLE_SNS_LOGIN_URL+"?"+parameterString;
        System.out.println("redirectURL = " + redirectURL);

        return redirectURL;
        /*
         * https://accounts.google.com/o/oauth2/v2/auth?scope=profile&response_type=code
         * &client_id="할당받은 id"&redirect_uri="access token 처리")
         * 로 Redirect URL을 생성하는 로직을 구성
         * */
    }

    //엑세스 토근 요청 메서드 추가
    public String requestAccessToken(String code) throws JsonProcessingException {
        //http 요청을 위한 헤더와 바디 설정
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("client_id", GOOGLE_SNS_CLIENT_ID);
        parameters.add("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        parameters.add("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        parameters.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                GOOGLE_SNS_TOKEN_BASE_URL,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            //응답에서 엑세스 토큰 추출
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            return jsonNode.get("access_token").asText();
        } else {
            throw new RuntimeException("구글 OAuth 액세스 토큰 요청 실패");
        }

    }

    // 사용자 정보 요청 메서드 추가
    public Map<String, Object> getUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        // 사용자 정보 요청
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                GOOGLE_SNS_USER_INFO_URL,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // 응답에서 사용자 정보 추출
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseEntity.getBody(), Map.class);
        } else {
            throw new RuntimeException("구글 OAuth 사용자 정보 요청 실패");
        }
    }

}
