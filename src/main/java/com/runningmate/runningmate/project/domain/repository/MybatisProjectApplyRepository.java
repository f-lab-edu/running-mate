package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import com.runningmate.runningmate.project.domain.mapper.ProjectApplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisProjectApplyRepository implements ProjectApplyRepository {

    private final ProjectApplyMapper projectApplyMapper;

    @Override
    public ProjectApply findByUserId(long userId) {
        return projectApplyMapper.selectProjectApplyByUserId(userId);
    }

    @Override
    public void save(ProjectApply projectApply) {
        projectApplyMapper.insertProjectApply(projectApply);
    }
}
