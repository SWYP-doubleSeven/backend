package com.swyp.doubleSeven.domain.badgeAcquire.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dao.BadgeAcquireDAO;
import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeAcquireServiceImpl implements BadgeAcquireService {

    private final BadgeAcquireDAO badgeAcquireDAO;

    public int insertBadgeAcquire(BadgeAcquireRequest badgeAcquireRequest) {
        return badgeAcquireDAO.insertBadgeAcquire(badgeAcquireRequest);
    }

    public List<Integer> getMaxMoneyMemberList() {
        return badgeAcquireDAO.getMaxMoneyMemberList();
    }

}
