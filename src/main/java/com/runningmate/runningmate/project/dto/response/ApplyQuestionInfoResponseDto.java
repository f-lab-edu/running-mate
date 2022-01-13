package com.runningmate.runningmate.project.dto.response;

import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyQuestionInfoResponseDto {
    private long applyQuestionId;
    private String question;

    public static ApplyQuestionInfoResponseDto of(ApplyQuestion applyQuestion) {
        return ApplyQuestionInfoResponseDto.builder()
            .applyQuestionId(applyQuestion.getApplyQuestionId())
            .question(applyQuestion.getQuestion())
            .build();
    }
}
