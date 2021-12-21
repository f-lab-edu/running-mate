package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectApplyMapper {

    public boolean existsByProjectPositionIdAndUserId(@Param("projectPositionId")long projectPositionId, @Param("userId")long userId);

    public void insertProjectApply(ProjectApply projectApply);

    public boolean existsByProjectPositionId(long projectPositionId);
}
