package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectApplyMapper {

    public ProjectApply selectProjectApplyByUserId(long userId);

    public void insertProjectApply(ProjectApply projectApply);
}
