package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {

    public List<Project> selectProjects(ProjectSearchRequestDto projectSearchRequestDto);

    public Project selectProject(long projectId);

    public void insertProject(Project project);
}
