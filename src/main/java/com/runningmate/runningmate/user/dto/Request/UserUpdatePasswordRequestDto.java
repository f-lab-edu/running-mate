package com.runningmate.runningmate.user.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordRequestDto {
    private String resetToken;
    private String password;
}
