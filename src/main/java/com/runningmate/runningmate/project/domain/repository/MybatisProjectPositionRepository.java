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
    public void save(ProjectPosition position) {
        projectPositionMapper.insertPosition(position);
    }

    @Override
    public void saveAll(List<ProjectPosition> positions) {
        projectPositionMapper.insertPositions(positions);
    }
}
