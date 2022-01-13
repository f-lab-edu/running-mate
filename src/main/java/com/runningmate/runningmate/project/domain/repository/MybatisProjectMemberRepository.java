package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectMember;
import com.runningmate.runningmate.project.domain.mapper.ProjectMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisProjectMemberRepository implements ProjectMemberRepository {

    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public void save(ProjectMember projectMember) {
        projectMemberMapper.insertProjectMember(projectMember);
    }

    @Override
    public void deleteProjectMemberByProjectPositionIdAndUserId(long projectPositionId, long userId) {
        projectMemberMapper.deleteProjectMemberByProjectPositionIdAndUserId(projectPositionId, userId);
    }
}
