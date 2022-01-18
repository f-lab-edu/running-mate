package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ProjectMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberInfoResponseDto {
    private long projectMemberId;
    private long projectPositionId;
    private long userId;

    public static ProjectMemberInfoResponseDto of(ProjectMember projectMember) {
        return ProjectMemberInfoResponseDto.builder()
            .projectMemberId(projectMember.getProjectMemberId())
            .projectPositionId(projectMember.getProjectPosition().getProjectPositionId())
            .userId(projectMember.getUser().getUserId())
            .build();
    }
}
