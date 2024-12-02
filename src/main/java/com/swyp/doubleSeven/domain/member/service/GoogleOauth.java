package com.swyp.doubleSeven.domain.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

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



}
