package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;

import java.util.List;

public interface ApplyQuestionRepository {

    public void save(ApplyQuestion applyQuestion);

    public void saveAll(List<ApplyQuestion> applyQuestions);
}
