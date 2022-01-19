package com.runningmate.runningmate.user.dto.Response;

import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import com.runningmate.runningmate.user.entity.UserProject;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectResponseDto {
    private long projectId;
    private String title;
    private ProjectStatus status;
    private LocalDate beginDate;
    private LocalDate endDate;

    private Position position;


    public static UserProjectResponseDto of(UserProject userProject) {
        return UserProjectResponseDto.builder()
            .projectId(userProject.getProjectId())
            .title(userProject.getTitle())
            .status(userProject.getStatus())
            .beginDate(userProject.getBeginDate())
            .endDate(userProject.getEndDate())
            .position(Position.builder()
                .positionId(userProject.getPosition().getPositionId())
                .positionName(userProject.getPosition().getPositionName())
                .build())
            .build();
    }
}