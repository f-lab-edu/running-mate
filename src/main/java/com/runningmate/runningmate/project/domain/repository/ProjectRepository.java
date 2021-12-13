package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import java.util.List;

public interface ProjectRepository {

    public List<Project> findAll(ProjectSearchRequestDto projectSearchRequestDto);

    public Project findByProjectId(long projectId);

    public void save(Project project);
}
