package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.Project;

public interface ProjectRepository {
    public Project findByProjectId(long projectId);
    public void save(Project project);
}
