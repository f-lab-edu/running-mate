package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.dto.UserPositionSaveDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.dto.UserSkillSaveDto;
import com.runningmate.runningmate.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class UserSaveDtoServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;


    @Test
    @DisplayName("로그인체크 테스트")
    public void testLoginCheck(){
        HttpSession session = mock(HttpSession.class);
        UserSaveDto userSaveDto = loginService.loginCheck("test@test.test", "password");


        assertEquals(true, userSaveDto != null);
        assertEquals(SessionUtils.getSession("LOGIN_USER_EMAIL"), session.getAttribute("LOGIN_USER_EMAIL"));
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void testUserSignUp(){

        UserPositionSaveDto userPositionSaveDto = UserPositionSaveDto.builder()
                .positionId(1)
                .positionName("BackEnd")
                .build();

        UserSkillSaveDto userSkillSaveDto1 = UserSkillSaveDto.builder()
                .skillId(1)
                .userId(1)
                .build();
        UserSkillSaveDto userSkillSaveDto2 = UserSkillSaveDto.builder()
                .skillId(2)
                .userId(2)
                .build();

        List<UserSkillSaveDto> userSkillSaveDtoList = new ArrayList<>();
        userSkillSaveDtoList.add(userSkillSaveDto1);
        userSkillSaveDtoList.add(userSkillSaveDto2);

        UserSaveDto userSaveDto = UserSaveDto.builder()
                        .email("test2@test2.com")
                        .nickName("닉네임")
                        .password("password12#$")
                        .resetToken("token")
                        .userPostition(userPositionSaveDto)
                        .userSkill(userSkillSaveDtoList)
                        .level(User.Level.valueOf("MANAGER"))
                        .build();


        userService.insertUser(userSaveDto);
        assertEquals(true, userSaveDto != null);
    }


}