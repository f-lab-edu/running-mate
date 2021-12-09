package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.aop.LoginCheck;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.service.LoginService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junsoo
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class LoginController {


    private final LoginService loginService;

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

        Optional<User> user = loginService.login(userLoginRequestDto);

        return user.isEmpty() ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그아웃
     *
     * @return
     */
    @LoginCheck(userLevel = UserLevel.CUSTOMER)
    @GetMapping("/logout")
    public ResponseEntity logout() {
        loginService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
