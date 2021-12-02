package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserSaveDtoServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("로그인 테스트")
    public void testLogin(){
        UserSaveDto userSaveDto = userService.login("tesA3t@naver.com", "testTEST1234!@#$");
        assertEquals(true, userSaveDto != null);
    }
}