package com.runningmate.runningmate.user.dto;

import com.runningmate.runningmate.user.aop.LoginCheck;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

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

    private String nickName;

    private LoginCheck.UserLevel level;

    private long positionId;

    @NotNull
    @Valid
    @Size(min = 1, message = "1개 이상의 스킬이 있어야 합니다.")
    private List<UserSkillSaveReqeustDto> userSkills;
}