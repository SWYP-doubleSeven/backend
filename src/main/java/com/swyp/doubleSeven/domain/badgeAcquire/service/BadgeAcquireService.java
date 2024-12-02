package com.swyp.doubleSeven.domain.badgeAcquire.service;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;

import java.util.List;

public interface BadgeAcquireService {

    int insertBadgeAcquire(BadgeAcquireRequest badgeAcquireRequest);

    List<Integer> getMaxMoneyMemberList();
}
