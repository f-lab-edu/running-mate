package com.runningmate.runningmate.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSkillSaveRequestDto {
    @Min(value = 1, message = "프로젝트에서 사용할 스킬을 입력해주세요")
    private long skillId;
}
