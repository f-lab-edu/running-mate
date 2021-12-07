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
public class ProjectPosition {

    private long projectPositionId;
    private long projectId;
    private long positionId;
    private int personnel;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
