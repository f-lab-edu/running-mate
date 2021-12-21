package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPosition {

    private long projectPositionId;
    private Project project;
    private Position position;
    private int personnel;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void updateInfo(ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto) {
        this.position = Position.builder().positionId(projectPositionUpdateRequestDto.getPositionId()).build();
        this.personnel = projectPositionUpdateRequestDto.getPersonnel();
        this.updateDate = LocalDateTime.now();
    }
}
