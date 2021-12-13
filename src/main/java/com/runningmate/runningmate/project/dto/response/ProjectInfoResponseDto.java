package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.Project;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoResponseDto {

    private long projectId;
    private String leaderEmail;
    private String leaderNickname;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String title;
    private String contents;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String imagePath;

    private List<ProjectPositionInfoResponseDto> positions;
    private List<ProjectSkillInfoResponseDto> skills;
    private List<ApplyQuestionInfoResponseDto> applyQuestions;

    public static ProjectInfoResponseDto of(Project project) {

        return ProjectInfoResponseDto.builder()
            .projectId(project.getProjectId())
            .leaderEmail(project.getLeader().getEmail())
            .leaderNickname(project.getLeader().getNickName())
            .beginDate(project.getBeginDate())
            .endDate(project.getEndDate())
            .title(project.getTitle())
            .contents(project.getContents())
            .status(project.getStatus().toString())
            .createDate(project.getCreateDate())
            .updateDate(project.getUpdateDate())
            .imagePath(project.getImage().getStorageFileName())
            .positions(project.getProjectPositions().stream()
                .map(ProjectPositionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .skills(project.getProjectSkills().stream()
                .map(ProjectSkillInfoResponseDto::of)
                .collect(Collectors.toList()))
            .applyQuestions(project.getApplyQuestions().stream()
                .map(ApplyQuestionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .build();
    }
}
