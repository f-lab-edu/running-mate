package com.runningmate.runningmate.project.domain.mapper;

import com.runningmate.runningmate.project.domain.entity.ApplyAnswer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyAnswerMapper {

    public void insertApplyAnswers(List<ApplyAnswer> applyAnswers);
}
