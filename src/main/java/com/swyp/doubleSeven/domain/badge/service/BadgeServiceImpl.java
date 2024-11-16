package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dao.BadgeDAO;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    private final BadgeDAO badgeDAO;

    // 뱃지 등록
    public BadgeResponse insertBadge(BadgeRequest badgeRequest) {
        badgeDAO.insertBadge(badgeRequest);
        return badgeDAO.selectBadge(BadgeRequest.builder().build().getBadgeId());
    }

    // 뱃지 수정
    public BadgeResponse updateBadge(BadgeRequest badgeRequest) {
        int result = badgeDAO.updateBadge(badgeRequest);
        if(result == 0) {
            throw new IllegalArgumentException("해당 뱃지 아이디가 존재하지 않습니다.");
        }

        return badgeDAO.selectBadge(badgeRequest.getBadgeId());
    }

    // 뱃지 삭제
    public int deleteBadge(Integer badgeId) {
        int result = badgeDAO.deleteBadge(badgeId);
        if(result == 0) {
            throw new IllegalArgumentException("해당 뱃지 아이디가 존재하지 않습니다.");
        }
        return result;
    }

    // 뱃지 단건조회


    // 뱃지 목록 조회

}
