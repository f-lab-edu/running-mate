package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyAnswer;
import com.runningmate.runningmate.project.domain.mapper.ApplyAnswerMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisApplyAnswerRepository implements ApplyAnswerRepository {

    private final ApplyAnswerMapper applyAnswerMapper;

    @Override
    public void saveAll(List<ApplyAnswer> applyAnswers) {
        applyAnswerMapper.insertApplyAnswers(applyAnswers);
    }
}
