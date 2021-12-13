package com.runningmate.runningmate.project.dto.request;

import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSearchRequestDto {
    private int cursor;
    private int size;
    private ProjectStatus status;
}
