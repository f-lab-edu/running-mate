package com.runningmate.runningmate.project.domain.repository;

import com.runningmate.runningmate.project.domain.entity.ApplyAnswer;
import java.util.List;

public interface ApplyAnswerRepository {

    public void saveAll(List<ApplyAnswer> applyAnswers);
}
