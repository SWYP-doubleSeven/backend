package com.swyp.doubleSeven.domain.badgeAcquire.dao;

import com.swyp.doubleSeven.domain.badgeAcquire.dto.request.BadgeAcquireRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BadgeAcquireDAO {

    int insertBadgeAcquire(BadgeAcquireRequest badgeAcquireRequest);

    List<Integer> getMaxMoneyMemberList();

    int insertBadgeAcquireAfterSaving(BadgeAcquireRequest badgeAcquireRequest);

    int insertBadgeAcquireAfterLogin(BadgeAcquireRequest badgeAcquireRequest);

}
