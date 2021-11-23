package com.runningmate.runningmate.user.dto;

import com.runningmate.runningmate.common.dto.Position;
import com.runningmate.runningmate.common.dto.Image;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * RequiredArgsConstructor
 * final 이나 NonNull인 필드들만 파라미터로 받는 생성자를 만들어 준다.
 *
 * @author junsoo
 */

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    // 이메일
    @NonNull
    private String email;

    // 비밀번호
    @NonNull
    private String password;

    // 닉네임
    @NonNull
    private String nickName;

    // 포지션
    private Position postition;

    // 이미지
    private Image userImage;

    // 비밀번호 초기화 토큰
    private String reset_token;


}
