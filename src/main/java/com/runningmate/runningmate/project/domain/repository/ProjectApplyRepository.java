package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import java.util.List;

public interface ProjectApplyRepository {

    public ProjectApply findByProjectApplyId(long projectApplyId);

    public List<ProjectApply> findByProjectId(long projectId);

    public void save(ProjectApply projectApply);

    public boolean existsByProjectId(long projectId);

    public boolean existsProjectPositionIdAndUserId(long projectPositionId, long userId);

    public boolean existsByProjectPositionId(long projectPositionId);

    public void update(ProjectApply projectApply);
}
