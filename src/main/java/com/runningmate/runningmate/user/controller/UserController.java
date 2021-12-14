package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * @author junsoo
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {


    private final UserService userService;

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
