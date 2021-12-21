package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;

public interface ProjectApplyRepository {

    public void save(ProjectApply projectApply);

    public boolean existsByProjectId(long projectId);

    public boolean existsProjectPositionIdAndUserId(long projectPositionId, long userId);

    public boolean existsByProjectPositionId(long projectPositionId);
}
