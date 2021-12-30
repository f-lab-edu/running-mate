package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyAnswer;
import java.util.List;

public interface ApplyAnswerRepository {

    List<ApplyAnswer> findByProjectApplyId(long projectApplyId);

    public void saveAll(List<ApplyAnswer> applyAnswers);
}
