package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectMemberMapper {

    public void insertProjectMember(ProjectMember projectMember);

    public void deleteProjectMemberByProjectPositionIdAndUserId(@Param("projectPositionId")long projectApplyId, @Param("userId")long userId);
}
