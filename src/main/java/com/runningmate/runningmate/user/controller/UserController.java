package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.exception.NotFoundUserException;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.dto.Request.UserUpdatePasswordRequestDto;
import com.runningmate.runningmate.user.aop.LoginCheck;
import com.runningmate.runningmate.user.dto.Request.UserUpdateRequestDto;
import com.runningmate.runningmate.user.dto.Response.UserInfoResponseDto;
import com.runningmate.runningmate.user.dto.Request.UserSignUpRequestDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity signUp(@Valid @RequestPart("user") UserSignUpRequestDto userSignUpRequestDto, @RequestPart("file") MultipartFile multipartFile) {
        userService.userRegister(userSignUpRequestDto, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 상세정보
     * 
     * @param userId 
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> getUser(@PathVariable("userId") long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(UserInfoResponseDto.of(user), HttpStatus.OK);
    }

    /**
     * 유저 업데이트
     *
     * @param userId
     * @param userUpdateRequestDto
     * @return
     */
    @LoginCheck
    @PatchMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> modifyUser(@PathVariable("userId") long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        if(SessionUtils.getLoginSessionUserId() != userId) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        userService.updateUser(userId, userUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/userPassword")
    public ResponseEntity<UserInfoResponseDto> modifyUserPassword(@RequestBody UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        userService.updateUserPassword(userUpdatePasswordRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 이미지 업데이트
     *
     * @param multipartFile
     * @return
     */
    @LoginCheck
    @PatchMapping("/{userId}/image")
    public ResponseEntity modifyUserImage(@PathVariable("userId") long userId, @RequestPart("file") MultipartFile multipartFile) {
        if(SessionUtils.getLoginSessionUserId() != userId) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        userService.updateUserImage(userId, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 삭제 ( ststus 변경 )
     *
     * @param userId
     * @return
     */
    @LoginCheck
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") long userId, @RequestBody String password ) {
        if(SessionUtils.getLoginSessionUserId() != userId) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        userService.deleteUser(userId, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user-password/{userEmail}")
    public ResponseEntity findUserPassword(@PathVariable("userEmail") String userEmail) {
        userService.findUserPassword(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
