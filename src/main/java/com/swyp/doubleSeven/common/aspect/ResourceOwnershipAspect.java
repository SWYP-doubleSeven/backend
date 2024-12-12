package com.swyp.doubleSeven.common.aspect;

import com.swyp.doubleSeven.common.exception.BusinessException;
import com.swyp.doubleSeven.domain.common.enums.Error;
import com.swyp.doubleSeven.domain.common.enums.LoginType;
import com.swyp.doubleSeven.domain.member.dao.GuestDAO;
import com.swyp.doubleSeven.domain.member.dto.response.guest.GuestLoginResponse;
import com.swyp.doubleSeven.domain.saving.dao.SavingDAO;
import com.swyp.doubleSeven.domain.saving.dto.response.SavingResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceOwnershipAspect {
    private final HttpServletRequest request;
    private final GuestDAO guestDAO;

    private final AuthenticationUtil authenticationUtil;
    private final SavingDAO savingDAO;

    @Before("@annotation(com.swyp.doubleSeven.common.annotation.ValidateResourceOwner) && args(savingId,..)")
    public void validateResourceOwner(JoinPoint joinPoint, Integer savingId) {
        Integer currentMemberId = authenticationUtil.getCurrentMemberId();
        if (currentMemberId == null) {
            throw new BusinessException(Error.LOGIN_REQUIRED);
        }

        SavingResponse saving = savingDAO.selectSaving(savingId, currentMemberId);
        if (saving == null) {
            throw new BusinessException(Error.RESOURCE_ACCESS_DENIED);
        }

        log.debug("Resource access validated - User: {}, Resource: {}", currentMemberId, savingId);
    }

}
