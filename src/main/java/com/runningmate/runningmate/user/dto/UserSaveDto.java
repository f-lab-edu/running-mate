package com.runningmate.runningmate.user.dto;

import com.runningmate.runningmate.common.dto.Position;
import com.runningmate.runningmate.common.dto.Image;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



/*
@Builder
모텔객체(dto) 생성시 Builder를 자동으로 생성해 준다.
build() 메소드를 통해 멤버변수에 필수값들을 null체크하고
이 멤버변수값을 이용해 빌더 클래스의 생성자를 호출하고 인스턴스를 리턴한다.
*/

@Getter
@Builder
public class UserSaveDto {

    // 이메일
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp="/^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+/",
            message = "이메일 형식에 맞게 입력해 주세요.")
    @Size(max = 50)
    @NotEmpty
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Size(min=8, max = 20)
    @NotEmpty
    private String password;

    // 닉네임
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(max = 20)
    @NotEmpty
    private String nickName;

    // 포지션
    private Position postition;

    // 이미지
    private Image userImage;

    // 비밀번호 초기화 토큰
    private String resetToken;


}
