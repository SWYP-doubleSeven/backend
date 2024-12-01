package com.swyp.doubleSeven.domain.badgeAcquire.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dao.BadgeAcquireDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeAcquireServiceImpl implements BadgeAcquireService {

    // todo : 로그인 구현시 BadgeAcquireDAO 메서드 추가(-)

    private final BadgeAcquireDAO badgeAcquireDAO;

}
