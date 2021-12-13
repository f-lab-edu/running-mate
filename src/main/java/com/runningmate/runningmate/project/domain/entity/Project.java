package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.user.entity.User;
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
    private User leader;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String title;
    private String contents;
    private ProjectStatus status;
    private int views;
    private Image image;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<ProjectPosition> projectPositions;
    private List<ProjectSkill> projectSkills;
    private List<ApplyQuestion> applyQuestions;
}
