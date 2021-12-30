package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import com.runningmate.runningmate.project.domain.mapper.ProjectApplyMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisProjectApplyRepository implements ProjectApplyRepository {

    private final ProjectApplyMapper projectApplyMapper;

    @Override
    public ProjectApply findByProjectApplyId(long projectApplyId) {
        return projectApplyMapper.selectProjectApplyByProjectApplyId(projectApplyId);
    }

    @Override
    public List<ProjectApply> findByProjectId(long projectId) {
        return projectApplyMapper.selectProjectApplyByProjectId(projectId);
    }

    @Override
    public void save(ProjectApply projectApply) {
        projectApplyMapper.insertProjectApply(projectApply);
    }

    @Override
    public boolean existsByProjectId(long projectId) {
        return projectApplyMapper.existsByProjectId(projectId);
    }

    @Override
    public boolean existsProjectPositionIdAndUserId(long projectPositionId, long userId) {
        return projectApplyMapper.existsByProjectPositionIdAndUserId(projectPositionId, userId);
    }

    @Override
    public boolean existsByProjectPositionId(long projectPositionId) {
        return projectApplyMapper.existsByProjectPositionId(projectPositionId);
    }

    @Override
    public void update(ProjectApply projectApply) {
        projectApplyMapper.updateProjectApply(projectApply);
    }
}
