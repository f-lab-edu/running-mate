package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.utils.ResponseMessage;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 *
 * RestController - ResponseBody + Controller
 * 주로 view가 아닌 Rest API 호출로 JSON 형태로 데이터 전송할때 사용
 *
 * RequiredArgsConstructor
 * - final 이나 NonNull인 필드들만 파라미터로 받는 생성자를 만들어 준다.
 * - 빈 생성자가 하나만 있고, 생성자의 파라미터 타입이 빈으로 등록 가능할 때
 * - @Autowire 없이 DI가 가능하다.
 *
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
     * @param requestUser
     * @param session
     * @return
     *
     * @author junsoo
     */
    @PostMapping
    public ResponseEntity login(@Valid @RequestBody UserSaveDto requestUserSaveDto, HttpSession session){
//        ResponseEntity
//         - 상태 응답 코드와, 응답메세지 등을 보낼 수 있다.
//         - HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있다.
        String email = requestUserSaveDto.getEmail();
        String password = requestUserSaveDto.getPassword();

        UserSaveDto userSaveDto = userService.loginCheck(email, password);
        if(userSaveDto == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else {
            userService.loginUser(session, email);
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
    @GetMapping
    public ResponseEntity logout(HttpSession session){
        userService.logout(session);
        return new ResponseEntity<>(ResponseMessage.LOGOUT_COMPLETE, HttpStatus.OK);
    }

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


}
