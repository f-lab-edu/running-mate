package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ProjectSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectSkillMapper {

    public ProjectSkill selectProjectSkill(long projectSkillId);

    public void insertProjectSkill(ProjectSkill skill);

    public void insertProjectSkills(List<ProjectSkill> skills);

    public void deleteProjectSkill(ProjectSkill projectSkill);
}
