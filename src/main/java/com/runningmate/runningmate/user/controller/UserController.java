package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.aop.LoginCheck;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author junsoo
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {


    private final UserService userService;

    /**
     * 로그인
     *
     * @param userLoginRequestDto
     * @return
     *
     * @author junsoo
     */
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {

        Optional<User> user = userService.login(userLoginRequestDto);
        if(user.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * 로그아웃
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseEntity logout() {
        userService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원가입
     *
     * @param userSaveDto
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody UserSaveDto userSaveDto) {
        userService.insertUser(userSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
