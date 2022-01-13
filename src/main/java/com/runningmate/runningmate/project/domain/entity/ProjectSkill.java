package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.skill.domain.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSkill {

    private long projectSkillId;
    private Project project;
    private Skill skill;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
