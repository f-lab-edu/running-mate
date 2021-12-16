package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.dto.UserSignUpRequestDto;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

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
     * @param userSignUpRequestDto
     * @param multipartFile
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestPart("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto, @RequestPart("file") MultipartFile multipartFile) {
        userService.userRegister(userSignUpRequestDto, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
