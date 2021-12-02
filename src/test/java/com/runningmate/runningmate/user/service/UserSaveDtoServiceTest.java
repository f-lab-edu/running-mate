package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("로그인 테스트")
    public void testUserLogin(){
        UserLoginRequestDto userLoginRequestDto =UserLoginRequestDto.builder()
                                            .email("tesA3t@naver.com")
                                            .password("testTEST1234!@#$")
                                            .build();
        UserSaveDto userSaveDto = userService.login(userLoginRequestDto);
        assertEquals(true, userSaveDto != null);
    }
}