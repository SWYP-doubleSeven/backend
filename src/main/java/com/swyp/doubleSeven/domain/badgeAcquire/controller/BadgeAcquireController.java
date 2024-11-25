package com.swyp.doubleSeven.domain.badgeAcquire.controller;

import com.swyp.doubleSeven.domain.badgeAcquire.service.BadgeAcquireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/badgeAcquire")
public class BadgeAcquireController {

    private final BadgeAcquireService badgeAcquireService;

}
