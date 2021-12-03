package com.runningmate.runningmate.project.domain.entity;

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
    private long projectId;
    private long skillId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
