package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectPositionMapper {

    public ProjectPosition selectProjectPosition(long projectPositionId);

    public boolean existsByProjectIdAndPositionId(@Param("projectId")long projectId, @Param("positionId")long positionId);

    public void insertProjectPosition(ProjectPosition projectPosition);

    public void insertProjectPositions(List<ProjectPosition> projectPositions);

    public void updateProjectPosition(ProjectPosition projectPosition);

    public void deleteProjectPosition(ProjectPosition projectPosition);
}
