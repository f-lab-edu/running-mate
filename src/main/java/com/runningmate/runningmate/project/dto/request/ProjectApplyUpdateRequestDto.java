package com.runningmate.runningmate.project.dto.request;

import com.runningmate.runningmate.project.domain.entity.ProjectApplyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplyUpdateRequestDto {
    private ProjectApplyStatus status;
}
