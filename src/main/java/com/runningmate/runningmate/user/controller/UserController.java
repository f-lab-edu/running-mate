package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.user.aop.LoginCheck;
import com.runningmate.runningmate.user.dto.Request.UserSkillAddReqeustDto;
import com.runningmate.runningmate.user.dto.Request.UserSkillSaveReqeustDto;
import com.runningmate.runningmate.user.dto.Response.UserInfoResponseDto;
import com.runningmate.runningmate.user.dto.Request.UserSignUpRequestDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.service.UserService;
import java.util.Optional;
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
    public ResponseEntity signUp(@Valid @RequestPart("userSignUpRequestDto") UserSignUpRequestDto userSignUpRequestDto, @RequestPart("file") MultipartFile multipartFile) {
        userService.userRegister(userSignUpRequestDto, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 상세정보
     * 
     * @param userId 
     * @return
     */
    @LoginCheck
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> getUser(@PathVariable("userId") long userId) {
        Optional<User> user = userService.getUser(userId);

        return user.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(UserInfoResponseDto.of(user.get()), HttpStatus.OK);
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
