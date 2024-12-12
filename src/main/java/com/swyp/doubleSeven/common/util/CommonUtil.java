package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.common.exception.AdminAccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    private CommonUtil() {} // 인스턴스 생성 방지

    // local ip일 경우 memberId를 1로 셋팅
    public static boolean isLocalEnvironment(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return "127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress);
    }
}
