package com.runningmate.runningmate.user.dto.Request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateSkillDto {
    private long skillId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}