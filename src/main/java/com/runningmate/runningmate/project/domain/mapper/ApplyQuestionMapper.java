package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyQuestionMapper {

    public ApplyQuestion selectApplyQuestion(long applyQuestionId);

    public void insertApplyQuestion(ApplyQuestion applyQuestion);

    public void insertApplyQuestions(List<ApplyQuestion> applyQuestions);

    public void updateApplyQuestion(ApplyQuestion applyQuestion);

    public void deleteApplyQuestion(ApplyQuestion applyQuestion);
}
