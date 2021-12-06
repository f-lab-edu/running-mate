package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.entity.User;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("로그인 테스트")
    public void testUserLogin(){
        UserLoginRequestDto userLoginRequestDto =UserLoginRequestDto.builder()
                                            .email("tesA3t@naver.com")
                                            .password("testTEST1234!@#$")
                                            .build();
        User user = userService.login(userLoginRequestDto);
        assertEquals(true, user != null);
    }

    @Test
    @DisplayName("이메일 형식에 안맞을 경우 실패")
    public void testUserLoginEmailNotValid(){
        UserLoginRequestDto userLoginRequestDto =UserLoginRequestDto.builder()
            .email("tesA3ttest")
            .password("testTEST1234!@#$")
            .build();
        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(userLoginRequestDto);
        ConstraintViolation<UserLoginRequestDto> violation = violations.iterator().next();
        assertEquals("이메일 형식에 맞지 않습니다.", violation.getMessage());
    }

    @Test
    @DisplayName("비밀번호 안맞을 경우 실패")
    public void testUserLoginPasswordFail(){
        UserLoginRequestDto userLoginRequestDto =UserLoginRequestDto.builder()
            .email("tesA3ttest@naver.com")
            .password("test")
            .build();
        User user = userService.login(userLoginRequestDto);
        assertEquals(false, user != null);
    }
}