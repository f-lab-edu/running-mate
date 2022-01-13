package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;

import java.util.List;

public interface ProjectPositionRepository {

    public ProjectPosition findById(long projectPositionId);

    public boolean existsByProjectIdAndPositionId(long projectId, long positionId);

    public void save(ProjectPosition projectPosition);

    public void saveAll(List<ProjectPosition> projectPositions);

    public void update(ProjectPosition projectPosition);

    public void delete(ProjectPosition projectPosition);
}
