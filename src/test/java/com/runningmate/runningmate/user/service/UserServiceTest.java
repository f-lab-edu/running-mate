package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.User;
import com.runningmate.runningmate.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@RequiredArgsConstructor
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("로그인체크 테스트")
    public void loginCheck(){
        User user = userService.loginCheck("test@test.test", "password");
        assertEquals(true, user != null);
    }


}