package com.runningmate.runningmate.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;


@Getter
@Builder
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
    private int postitionId;

    // 이미지
    private int imageId;

    // 비밀번호 초기화 토큰
    private String resetToken;

    // 수정날짜
    private LocalDateTime updateDate;
    
    // 생성날짜
    private LocalDateTime createDate;


}
