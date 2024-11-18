package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.common.exception.AdminAccessDeniedException;
import jakarta.servlet.http.HttpSession;

public class CommonUtil {

    private CommonUtil() {} // 인스턴스 생성 방지

    // 관리자 권한 검증
    public static void validateAdminAccess(HttpSession session) {
        session.setAttribute("roleCd", "ADMIN"); // TODO : 삭제예정
        String roleCd = String.valueOf(session.getAttribute("roleCd"));
        if(!"ADMIN".equals(roleCd)) {
            throw new AdminAccessDeniedException("관리자 권한이 필요합니다");
        }
    }
}
