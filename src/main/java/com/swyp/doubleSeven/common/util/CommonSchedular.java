package com.swyp.doubleSeven.common.util;

import com.swyp.doubleSeven.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommonSchedular {

    private final MemberService memberService;

    // 매일 자정에 비활성 회원 삭제
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void deleteInactiveMembersTask() {
        memberService.deleteOldMember();
        log.info("Inactive members deleted successfully at: " + java.time.LocalDateTime.now());
    }

}
