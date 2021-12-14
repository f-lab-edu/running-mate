package com.runningmate.runningmate.user;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.entity.User;

import com.runningmate.runningmate.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    UserRepository mybatisUserRepository;

    @Test
    @DisplayName("이메일이 없으면 실패")
    public void testExistEmail(){
        String email = "test@test.naver.com";
        Optional<User> user = mybatisUserRepository.findByEmail(email);
        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("비밀번호 안맞을 경우 실패")
    public void testUserLoginPasswordFail(){
        String email = "test@test.test";
        String password = "test";
        User returnUser = User.builder()
            .userId(1)
            .email(email)
            .password("$2a$10$yB1ffK2pfCKKSkR3qUsXHuQ2KOVo49xgdHc5o/nadRH5K1ZwrhDw6")
            .resetToken("123")
            .imageId(2)
            .positionId(3)
            .nickName("1")
            .level(UserLevel.CUSTOMER)
            .build();
        when(mybatisUserRepository.findByEmail(email)).thenReturn(Optional.of(returnUser));

        Optional<User> passwordCheckUser = mybatisUserRepository.findByEmail(email);
        assertFalse(BCryptUtil.comparePassword(password, passwordCheckUser.get().getPassword()));
    }
}