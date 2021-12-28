package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyQuestion {

    private long applyQuestionId;
    private Project project;
    private String question;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void updateInfo(ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto) {
        this.question = applyQuestionUpdateRequestDto.getQuestion();
        this.updateDate = LocalDateTime.now();
    }
}
