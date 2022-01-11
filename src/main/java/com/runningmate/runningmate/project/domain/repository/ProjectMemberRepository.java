package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectMember;

public interface ProjectMemberRepository {

    public void save(ProjectMember projectMember);

    public void deleteProjectMemberByProjectPositionIdAndUserId(long projectPositionId, long userId);
}
