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
    public void save(ProjectSkill skill) {
        projectSkillMapper.insertSkill(skill);
    }

    @Override
    public void saveAll(List<ProjectSkill> skills) {
        projectSkillMapper.insertSkills(skills);
    }
}
