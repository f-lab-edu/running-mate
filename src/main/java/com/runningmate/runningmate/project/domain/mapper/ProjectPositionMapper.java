package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectPositionMapper {
    public void insertPosition(ProjectPosition position);
    public void insertPositions(List<ProjectPosition> positions);
}
