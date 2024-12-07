package com.swyp.doubleSeven.domain.member.dto.response;

import com.swyp.doubleSeven.domain.badge.dto.response.BadgeResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    private Integer memberId;
    private String memberKeyId;
    private String memberNickname;
    private String memberName;
    private String email;
    private String role;
    private String loginType;
    private String dltnYn;
    private LocalDateTime rgstDt;
    private LocalDateTime updtDt;
    private List<BadgeResponse> badgeResponseList;
}
