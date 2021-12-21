package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;

public interface ProjectApplyRepository {

    public boolean existsProjectPositionIdAndUserId(long projectPositionId, long userId);

    public void save(ProjectApply projectApply);

    public boolean existsByProjectPositionId(long projectPositionId);
}
