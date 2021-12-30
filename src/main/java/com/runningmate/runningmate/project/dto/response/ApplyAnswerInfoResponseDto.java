package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ApplyAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyAnswerInfoResponseDto {
    private long applyAnswerId;
    private long applyQuestionId;
    private String answer;
    private String question;

    public static ApplyAnswerInfoResponseDto of(ApplyAnswer applyAnswer) {
        return ApplyAnswerInfoResponseDto.builder()
            .applyAnswerId(applyAnswer.getApplyAnswerId())
            .applyQuestionId(applyAnswer.getApplyQuestion().getApplyQuestionId())
            .answer(applyAnswer.getAnswer())
            .question(applyAnswer.getApplyQuestion().getQuestion())
            .build();
    }
}
