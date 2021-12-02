package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;

import java.util.List;

public interface ProjectPositionRepository {
    public void save(ProjectPosition position);
    public void saveAll(List<ProjectPosition> positions);
}
