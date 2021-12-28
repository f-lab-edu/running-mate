package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectApplyMapper {

    public void insertProjectApply(ProjectApply projectApply);

    public boolean existsByProjectId(long projectId);

    public boolean existsByProjectPositionIdAndUserId(@Param("projectPositionId")long projectPositionId, @Param("userId")long userId);

    public boolean existsByProjectPositionId(long projectPositionId);
}
