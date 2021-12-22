package com.runningmate.runningmate.user.dto.Request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@Builder
public class UserLoginRequestDto {

    // 이메일
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message="이메일 형식에 맞지 않습니다.")
    @Size(max = 50)
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Size(min=8, max = 20)
    private String password;
}
