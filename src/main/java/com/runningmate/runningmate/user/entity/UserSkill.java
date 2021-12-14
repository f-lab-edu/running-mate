package com.runningmate.runningmate.user.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {
    long userSkillId;
    long userId;
    long skillId;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;
}
