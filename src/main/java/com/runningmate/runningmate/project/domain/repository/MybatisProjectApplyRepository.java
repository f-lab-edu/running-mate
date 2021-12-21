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
    public boolean existsProjectPositionIdAndUserId(long projectPositionId, long userId) {
        return projectApplyMapper.existsByProjectPositionIdAndUserId(projectPositionId, userId);
    }

    @Override
    public void save(ProjectApply projectApply) {
        projectApplyMapper.insertProjectApply(projectApply);
    }

    @Override
    public boolean existsByProjectPositionId(long projectPositionId) {
        return projectApplyMapper.existsByProjectPositionId(projectPositionId);
    }
}
