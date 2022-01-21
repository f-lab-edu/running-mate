package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.domain.entity.ProjectMember;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    private int likeCount;
    private boolean likePushed;

    private List<ProjectPositionInfoResponseDto> positions;
    private List<ProjectSkillInfoResponseDto> skills;
    private List<ApplyQuestionInfoResponseDto> applyQuestions;
    private List<ProjectMemberInfoResponseDto> members;

    public static ProjectInfoResponseDto of(Project project, Map<Long, Integer> likeCountMap) {

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
            .likeCount(likeCountMap.get(project.getProjectId()))
            .positions(project.getProjectPositions().stream()
                .map(ProjectPositionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .skills(project.getProjectSkills().stream()
                .map(ProjectSkillInfoResponseDto::of)
                .collect(Collectors.toList()))
            .applyQuestions(project.getApplyQuestions().stream()
                .map(ApplyQuestionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .members(project.getProjectMembers().stream()
                .map(ProjectMemberInfoResponseDto::of)
                .collect(Collectors.toList()))
            .build();
    }

    public static ProjectInfoResponseDto of(Project project, Map<Long, Integer> likeCountMap, Map<Long, Boolean> likeExistMap) {

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
            .likeCount(likeCountMap.get(project.getProjectId()))
            .likePushed(likeExistMap.get(project.getProjectId()))
            .positions(project.getProjectPositions().stream()
                .map(ProjectPositionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .skills(project.getProjectSkills().stream()
                .map(ProjectSkillInfoResponseDto::of)
                .collect(Collectors.toList()))
            .applyQuestions(project.getApplyQuestions().stream()
                .map(ApplyQuestionInfoResponseDto::of)
                .collect(Collectors.toList()))
            .members(project.getProjectMembers().stream()
                .map(ProjectMemberInfoResponseDto::of)
                .collect(Collectors.toList()))
            .build();
    }
}
