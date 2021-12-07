package com.runningmate.runningmate.user.entity;

import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 유저 고유 ID
    private long userId;

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
    private long positionId;

    // 이미지
    private long imageId;

    // 비밀번호 초기화 토큰
    private String resetToken;

    // 수정날짜
    private LocalDateTime updateDate;

    // 생성날짜
    private LocalDateTime createDate;

    // 권한
    private UserLevel level;
}
