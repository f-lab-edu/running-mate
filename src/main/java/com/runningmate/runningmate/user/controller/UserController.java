package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.annotation.SessionLoginUser;
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
     * @param loginUser
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> getUser(@SessionLoginUser User loginUser) {
        return new ResponseEntity<>(UserInfoResponseDto.of(loginUser), HttpStatus.OK);
    }

    /**
     * 유저 업데이트
     *
     * @param loginUser
     * @param userUpdateRequestDto
     * @return
     */
    @LoginCheck
    @PatchMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> modifyUser(@SessionLoginUser User loginUser, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.updateUser(loginUser, userUpdateRequestDto);
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
    public ResponseEntity modifyUserImage(@SessionLoginUser User loginUser, @RequestPart("file") MultipartFile multipartFile) {
        userService.updateUserImage(loginUser, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 삭제 ( ststus 변경 )
     *
     * @param loginUser
     * @param password
     * @return
     */
    @LoginCheck
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@SessionLoginUser User loginUser, @RequestBody String password ) {
        userService.deleteUser(loginUser, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user-password/{userEmail}")
    public ResponseEntity findUserPassword(@PathVariable("userEmail") String userEmail) {
        userService.findUserPassword(userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @LoginCheck
    @PatchMapping("/user-skill/{userId}/{userSkillId}")
    public ResponseEntity<UserInfoResponseDto> modifyUserSkill(@PathVariable("userId") long userId, @PathVariable("userSkillId") long userSkillId, @RequestBody @Valid UserSkillSaveReqeustDto UpdateUserSkillReqeustDto){
        userService.updateUserSkill(userId, userSkillId, UpdateUserSkillReqeustDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginCheck
    @DeleteMapping("/user-skill/{userSkillId}")
    public ResponseEntity<UserInfoResponseDto> deleteUserSkill(@PathVariable("userSkillId") long deleteUserSkillId){
        userService.deleteUserSkill(deleteUserSkillId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginCheck
    @PostMapping("/user-skill/{userId}")
    public ResponseEntity<UserInfoResponseDto> addUserSkill(@PathVariable("userId") long userId, @RequestBody @Valid UserSkillAddReqeustDto userSkillAddReqeustDto){
        userService.insertUserSkills(userId, userSkillAddReqeustDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
