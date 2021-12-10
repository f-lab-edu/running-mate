package com.runningmate.runningmate.user.dto;

import com.runningmate.runningmate.user.aop.LoginCheck;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {
    // 유저 고유 Id
    private long userId;

    // 이메일
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    @Size(max = 50)
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Size(max = 20)
    private String password;

    // 닉네임
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(max = 20)
    private String nickName;

    // 포지션
    private long postitionId;

    // 이미지
    private long imageId;

    // 비밀번호 초기화 토큰
    private String resetToken;

    // 권한
    private LoginCheck.UserLevel level;
}
