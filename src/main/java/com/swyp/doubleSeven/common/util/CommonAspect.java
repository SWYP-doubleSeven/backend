package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import com.swyp.doubleSeven.domain.badgeAcquire.controller.BadgeAcquireController;
import com.swyp.doubleSeven.domain.badgeCount.controller.BadgeCountController;
import com.swyp.doubleSeven.domain.badgeCount.dto.request.BadgeCountRequest;
import com.swyp.doubleSeven.domain.common.enums.BadgeType;
import com.swyp.doubleSeven.domain.saving.dto.request.SavingRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@AllArgsConstructor
public class CommonAspect {

    private final BadgeCountController badgeCountController;
    private final BadgeAcquireController badgeAcquireController;

    /* 로그인시 연속출석 기록, 출석카운트 증가 */
    public List<BadgeResponse> afterLogin(Integer memberId) {

        int result = badgeCountController.upsertMemberAttendance(memberId);
        if(result > 0) { // 당일 로그인 2회이상->카운트X
            LocalDate cureentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String yyyymmdd = cureentDate.format(formatter);

            return badgeAcquireController.insertBadgeAcquireAfterLogin(memberId, yyyymmdd);
        }
        return new ArrayList();
    }

    public List<BadgeResponse> afterSaving(Integer memberId) {
        return badgeAcquireController.insertBadgeAcquireAfterSaving(memberId);
    }
}
