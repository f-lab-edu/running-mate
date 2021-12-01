package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.utils.ResponseMessage;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LoginController {


    private final LoginService loginService;

    /**
     * 로그인
     *
     * @param requestUser
     * @param session
     * @return
     *
     * @author junsoo
     */
    @PostMapping("/user/login")
    public ResponseEntity login(@Valid @RequestBody UserSaveDto requestUserSaveDto, HttpSession session){
        String email = requestUserSaveDto.getEmail();
        String password = requestUserSaveDto.getPassword();

        UserSaveDto userSaveDto = loginService.loginCheck(email, password);
        if(userSaveDto == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else {
            loginService.loginUser(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * 로그아웃
     *
     * @param requestUser
     * @param session
     * @return
     */
    @GetMapping("/user/logout")
    public ResponseEntity logout(){
        loginService.logout();
        return new ResponseEntity<>(ResponseMessage.LOGOUT_COMPLETE, HttpStatus.OK);
    }


}
