package com.runningmate.runningmate.project.domain.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project {
    private long projectId;
    private long leader;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String title;
    private String contents;
    private ProjectStatus status;
    private int views;
    private long imageId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<ProjectPosition> projectPositions;
    private List<ProjectSkill> projectSkills;
    private List<ApplyQuestion> applyQuestions;
}
