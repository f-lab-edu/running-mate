package com.runningmate.runningmate.user.dto.Request;

import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String nickName;
    private String password;
    private long positionId;
    private UserLevel level;
}