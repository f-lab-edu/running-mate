package com.runningmate.runningmate.skill.domain.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Skill {
    private long skillId;
    private String skillName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
