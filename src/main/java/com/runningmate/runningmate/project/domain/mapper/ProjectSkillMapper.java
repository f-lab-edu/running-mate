package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectSkillMapper {

    public void insertSkill(ProjectSkill skill);

    public void insertSkills(List<ProjectSkill> skills);
}
