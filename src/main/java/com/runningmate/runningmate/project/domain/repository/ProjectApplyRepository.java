package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ProjectApply;

public interface ProjectApplyRepository {

    public ProjectApply findByUserId(long userId);

    public void save(ProjectApply projectApply);
}
