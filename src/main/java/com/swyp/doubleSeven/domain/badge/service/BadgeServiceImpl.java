package com.swyp.doubleSeven.domain.badge.service;

import com.swyp.doubleSeven.domain.badge.dao.BadgeDAO;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeRequest;
import com.swyp.doubleSeven.domain.badge.dto.request.BadgeSearchCriteria;
import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    private final BadgeDAO badgeDAO;

    // 뱃지 등록
    @Override
    public BadgeResponse insertBadge(BadgeRequest badgeRequest) {
        badgeDAO.insertBadge(badgeRequest);
        return badgeDAO.getBadge(badgeRequest.getBadgeId());
    }

    // 뱃지 수정
    @Override
    public BadgeResponse updateBadge(BadgeRequest badgeRequest) {
        int result = badgeDAO.updateBadge(badgeRequest);
        if(result == 0) {
            throw new IllegalArgumentException("해당 뱃지 아이디가 존재하지 않습니다.");
        }

        return badgeDAO.getBadge(badgeRequest.getBadgeId());
    }

    // 뱃지 삭제
    @Override
    public int deleteBadge(Integer badgeId) {
        int result = badgeDAO.deleteBadge(badgeId);
        if(result == 0) {
            throw new IllegalArgumentException("해당 뱃지 아이디가 존재하지 않습니다.");
        }
        return result;
    }

    // 뱃지 단건조회
    @Override
    public BadgeResponse getBadge(Integer badgeId) {
        BadgeResponse badge = badgeDAO.getBadge(badgeId);
        if(badge == null) {
            throw new IllegalArgumentException("해당 뱃지가 존재하지 않습니다.");
        }
        return badge;
    }

    // 뱃지 목록 조회
    @Override
    public List<BadgeResponse> getBadgeList(BadgeSearchCriteria criteria) {
        return badgeDAO.getBadgeList(criteria);
    }


}