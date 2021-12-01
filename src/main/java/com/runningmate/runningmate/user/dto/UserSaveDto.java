package com.runningmate.runningmate.user.dto;

import com.runningmate.runningmate.aop.LoginCheck;
import com.runningmate.runningmate.common.entity.Image;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {

    // 이메일
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Size(max = 50)
    @NotEmpty
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Size(min=8, max = 20)
    @NotEmpty
    private String password;

    // 닉네임
    private String nickName;

    // 포지션
    private UserPositionSaveDto userPostition;

    // 이미지
    private Image image;

//    스킬
    private List<UserSkillSaveDto> userSkill;

    // 비밀번호 초기화 토큰
    private String resetToken;

    // 권한
    private LoginCheck.UserLevel level;


}
