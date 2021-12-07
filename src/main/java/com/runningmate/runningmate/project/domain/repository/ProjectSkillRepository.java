package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectSkill;

import java.util.List;

public interface ProjectSkillRepository {

    public void save(ProjectSkill skill);

    public void saveAll(List<ProjectSkill> skills);
}
