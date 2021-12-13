package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPositionInfoResponseDto {

    private long projectPositionId;
    private long positionId;
    private String positionName;
    private int personnel;

    public static ProjectPositionInfoResponseDto of(ProjectPosition projectPosition) {
        return ProjectPositionInfoResponseDto.builder()
            .projectPositionId(projectPosition.getProjectPositionId())
            .positionId(projectPosition.getPosition().getPositionId())
            .positionName(projectPosition.getPosition().getPositionName())
            .personnel(projectPosition.getPersonnel())
            .build();
    }
}
