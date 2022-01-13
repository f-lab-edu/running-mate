package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectSkill;
import com.runningmate.runningmate.project.domain.mapper.ProjectSkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisProjectSkillRepository implements ProjectSkillRepository {

    private final ProjectSkillMapper projectSkillMapper;

    @Override
    public ProjectSkill findByProjectSkillId(long projectSkillId) {
        return projectSkillMapper.selectProjectSkill(projectSkillId);
    }

    @Override
    public void save(ProjectSkill skill) {
        projectSkillMapper.insertProjectSkill(skill);
    }

    @Override
    public void saveAll(List<ProjectSkill> skills) {
        projectSkillMapper.insertProjectSkills(skills);
    }

    @Override
    public void delete(ProjectSkill projectSkill) {
        projectSkillMapper.deleteProjectSkill(projectSkill);
    }
}
