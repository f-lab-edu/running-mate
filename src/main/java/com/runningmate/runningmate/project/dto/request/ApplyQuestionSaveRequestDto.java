package com.runningmate.runningmate.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyQuestionSaveRequestDto {

    @NotBlank(message = "질문을 입력해주세요")
    private String question;
}
