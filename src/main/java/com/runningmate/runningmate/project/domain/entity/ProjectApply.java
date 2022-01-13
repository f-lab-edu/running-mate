package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.project.dto.request.ProjectApplyUpdateRequestDto;
import com.runningmate.runningmate.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectApply {

    private long projectApplyId;
    private ProjectPosition projectPosition;
    private User user;
    private ProjectApplyStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void updateInfo(ProjectApplyUpdateRequestDto projectApplyUpdateRequestDto) {
        this.status = projectApplyUpdateRequestDto.getStatus();
        this.updateDate = LocalDateTime.now();
    }
}
