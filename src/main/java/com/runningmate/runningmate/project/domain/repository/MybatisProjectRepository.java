package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.domain.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MybatisProjectRepository implements ProjectRepository {

    private final ProjectMapper projectMapper;

    @Override
    public Project findByProjectId(long projectId) {
        return projectMapper.selectProject(projectId);
    }

    @Override
    public void save(Project project) {
        projectMapper.insertProject(project);
    }
}