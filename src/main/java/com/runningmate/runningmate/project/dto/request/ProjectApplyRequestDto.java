package com.runningmate.runningmate.project.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplyRequestDto {

    @Min(value = 1, message = "질문 아이디는 필수 입니다.")
    private long applyQuestionId;

    @NotBlank(message = "답변을 입력해주세요.")
    private String answer;
}
