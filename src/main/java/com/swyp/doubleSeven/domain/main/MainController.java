package com.swyp.doubleSeven.domain.main;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class MainController {

    // 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다 테스트입니다

    // 기본 URL(/)로 접근 시, test.html로 리다이렉트
    @GetMapping("/")
    public ResponseEntity<String> home() throws IOException {
        Resource resource = new ClassPathResource("templates/test.html");
        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}