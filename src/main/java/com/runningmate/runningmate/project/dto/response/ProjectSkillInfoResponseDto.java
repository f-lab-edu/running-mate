package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ProjectSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSkillInfoResponseDto {

    private long projectSkillId;
    private long skillId;
    private String skillName;

    public static ProjectSkillInfoResponseDto of(ProjectSkill projectSkill) {
        return ProjectSkillInfoResponseDto.builder()
            .projectSkillId(projectSkill.getProjectSkillId())
            .skillId(projectSkill.getSkill().getSkillId())
            .skillName(projectSkill.getSkill().getSkillName())
            .build();
    }
}
