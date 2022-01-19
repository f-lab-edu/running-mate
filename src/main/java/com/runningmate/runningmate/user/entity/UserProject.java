package com.runningmate.runningmate.user.entity;

import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProject {

    private long projectId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String title;
    private ProjectStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private Position position;
}
