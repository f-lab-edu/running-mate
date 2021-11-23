package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.User;
import lombok.RequiredArgsConstructor;
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
    public void loginCheck(){
        Optional<User> user = userService.loginCheck("1", "password");
        assertEquals(true, BCrypt.checkpw("password", user.get().getPassword()));
    }

    @Test
    public void insertUser(){

    }

}