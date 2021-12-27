package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.dto.Request.UserLoginRequestDto;
import com.runningmate.runningmate.user.entity.User;
import java.util.Optional;
import javax.validation.Valid;

import com.runningmate.runningmate.user.service.SessionLoginService;
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


    private final SessionLoginService sessionLoginService;

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

        Optional<User> user = sessionLoginService.login(userLoginRequestDto);

        return user.isEmpty() ? new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그아웃
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseEntity logout() {
        sessionLoginService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
