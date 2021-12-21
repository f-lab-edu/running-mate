package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectPosition;
import com.runningmate.runningmate.project.domain.mapper.ProjectPositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisProjectPositionRepository implements ProjectPositionRepository {

    private final ProjectPositionMapper projectPositionMapper;

    @Override
    public ProjectPosition findById(long projectPositionId) {
        return projectPositionMapper.selectProjectPosition(projectPositionId);
    }

    @Override
    public boolean existsByProjectIdAndPositionId(long projectId, long positionId) {
        return projectPositionMapper.existsByProjectIdAndPositionId(projectId, positionId);
    }

    @Override
    public void save(ProjectPosition projectPosition) {
        projectPositionMapper.insertProjectPosition(projectPosition);
    }

    @Override
    public void saveAll(List<ProjectPosition> projectPositions) {
        projectPositionMapper.insertProjectPositions(projectPositions);
    }

    @Override
    public void update(ProjectPosition projectPosition) {
        projectPositionMapper.updateProjectPosition(projectPosition);
    }

    @Override
    public void delete(ProjectPosition projectPosition) {
        projectPositionMapper.deleteProjectPosition(projectPosition);
    }
}
