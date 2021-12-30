package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import com.runningmate.runningmate.project.domain.entity.ProjectApplyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplyInfoResponseDto {
    private long projectApplyId;
    private ProjectApplyStatus status;
    private long projectPositionId;
    private String positionName;
    private long userId;
    private String email;
    private String nickname;

    public static ProjectApplyInfoResponseDto of(ProjectApply projectApply) {
        return ProjectApplyInfoResponseDto.builder()
            .projectApplyId(projectApply.getProjectApplyId())
            .status(projectApply.getStatus())
            .projectPositionId(projectApply.getProjectPosition().getProjectPositionId())
            .positionName(projectApply.getProjectPosition().getPosition().getPositionName())
            .userId(projectApply.getUser().getUserId())
            .email(projectApply.getUser().getEmail())
            .nickname(projectApply.getUser().getNickName())
            .build();
    }
}
