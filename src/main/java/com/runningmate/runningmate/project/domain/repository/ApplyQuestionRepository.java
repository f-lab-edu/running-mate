package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;

import java.util.List;

public interface ApplyQuestionRepository {

    public ApplyQuestion findByApplyQuestionId(long applyQuestionId);

    public void save(ApplyQuestion applyQuestion);

    public void saveAll(List<ApplyQuestion> applyQuestions);

    public void update(ApplyQuestion applyQuestion);

    public void delete(ApplyQuestion applyQuestion);
}
