package com.runningmate.runningmate.project.domain.entity;

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
public class ProjectMember {
    private long projectMemberId;
    private ProjectPosition projectPosition;
    private User user;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
