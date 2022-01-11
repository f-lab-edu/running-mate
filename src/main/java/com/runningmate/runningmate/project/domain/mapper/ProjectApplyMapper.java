package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectApplyMapper {

    public ProjectApply selectProjectApplyByProjectApplyId(long projectApplyId);

    public List<ProjectApply> selectProjectApplyByProjectId(long projectId);

    public void insertProjectApply(ProjectApply projectApply);

    public boolean existsByProjectId(long projectId);

    public boolean existsByProjectPositionIdAndUserId(@Param("projectPositionId")long projectPositionId, @Param("userId")long userId);

    public boolean existsByProjectPositionId(long projectPositionId);

    public void updateProjectApply(ProjectApply projectApply);
}
