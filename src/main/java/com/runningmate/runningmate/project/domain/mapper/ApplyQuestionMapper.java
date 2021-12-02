package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyQuestionMapper {
    public void insertApplyQuestion(ApplyQuestion applyQuestion);
    public void insertApplyQuestions(List<ApplyQuestion> applyQuestions);
}
