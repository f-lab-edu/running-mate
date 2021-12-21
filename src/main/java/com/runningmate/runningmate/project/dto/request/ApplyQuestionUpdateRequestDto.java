package com.runningmate.runningmate.project.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyQuestionUpdateRequestDto {

    @NotBlank(message = "질문을 입력해주세요")
    private String question;
}
