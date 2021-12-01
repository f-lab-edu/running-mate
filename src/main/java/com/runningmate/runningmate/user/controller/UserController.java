package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.utils.ResponseMessage;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 *
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
     * @param requestUserSaveDto
     * @param session
     * @return
     */
    @PostMapping("signUp")
    public ResponseEntity signUp(@Valid @RequestBody UserSaveDto requestUserSaveDto){
        userService.insertUser(requestUserSaveDto);
        return new ResponseEntity<>(ResponseMessage.USER_REGIST_COMPLETE, HttpStatus.OK);
    }


    /**
     * 로그아웃
     *
     * @param requestUser
     * @param session
     * @return
     */
    @GetMapping("/user/checkId/{email}")
    public ResponseEntity checkId(@PathVariable @NotNull String email){
        Optional<User> user = userService.findByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(ResponseMessage.USER_CHECK_ID_COMPLETE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseMessage.USER_CHECK_ID_FAIL, HttpStatus.UNAUTHORIZED);
        }
    }


}
