package com.runningmate.runningmate.user.entity;

import com.runningmate.runningmate.skill.domain.entity.Skill;
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
    private long userSkillId;
    private User user;
    private Skill skill;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;
}
