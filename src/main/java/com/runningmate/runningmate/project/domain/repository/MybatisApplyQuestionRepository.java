package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;
import com.runningmate.runningmate.project.domain.mapper.ApplyQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisApplyQuestionRepository implements ApplyQuestionRepository {

    private final ApplyQuestionMapper applyQuestionMapper;

    @Override
    public void save(ApplyQuestion applyQuestion) {
        applyQuestionMapper.insertApplyQuestion(applyQuestion);
    }

    @Override
    public void saveAll(List<ApplyQuestion> applyQuestions) {
        applyQuestionMapper.insertApplyQuestions(applyQuestions);
    }
}
