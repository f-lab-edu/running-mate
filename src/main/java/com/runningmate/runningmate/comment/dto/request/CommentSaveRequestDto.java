package com.runningmate.runningmate.comment.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 250, message = "250자 이상 작성할 수 없습니다.")
    private String contents;
}
