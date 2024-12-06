package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dao.UserBadgeDAO;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBadgeServiceImpl implements UserBadgeService {

    private final UserBadgeDAO userBadgeDAO;

    @Override
    public BadgeResponse getBadge(BadgeRequest request) {
        return userBadgeDAO.getBadge(request);
    }

    // 뱃지 목록 조회
    @Override
    public List<BadgeResponse> getBadgeList(Integer memberId) {
        return userBadgeDAO.getBadgeList(memberId);
    }


    @Override
    public List<BadgeResponse> getBadgeIdAfterSaving(BadgeRequest badgeRequest) {
        return userBadgeDAO.getBadgeIdAfterSaving(badgeRequest);
    }

    @Override
    public List<BadgeResponse> getBadgeIdAfterLogin(BadgeRequest badgeRequest) {
        return userBadgeDAO.getBadgeIdAfterLogin(badgeRequest);
    }
}
