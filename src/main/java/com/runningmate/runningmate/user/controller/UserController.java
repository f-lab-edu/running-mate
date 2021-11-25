package com.runningmate.runningmate.user.controller;

import com.runningmate.runningmate.common.utils.ResponseMessage;
import com.runningmate.runningmate.user.dto.User;
import com.runningmate.runningmate.user.entity.UserInfo;
import com.runningmate.runningmate.user.service.UserService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    @PostMapping("login")
    public ResponseEntity login(@RequestBody User requestUser, HttpSession session){
//        ResponseEntity
//         - 상태 응답 코드와, 응답메세지 등을 보낼 수 있다.
//         - HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있다.

        String email = requestUser.getEmail();
        String password = requestUser.getPassword();

        User user = userService.loginCheck(email, password);
        if(user == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else {
            userService.loginUser(session, email);
            Map<String, Object> rmap = new HashMap<String, Object>();
            rmap.put("data", user);
            return new ResponseEntity<>(rmap, HttpStatus.OK);
        }
    }

    /**
     * 로그아웃
     *
     * @param requestUser
     * @param session
     * @return
     */
    @GetMapping("logout")
    public ResponseEntity logout(HttpSession session){
        userService.logout(session);
        return new ResponseEntity<>(ResponseMessage.LOGOUTCOMPLETE, HttpStatus.OK );
    }

    /**
     * 회원가입
     *
     * @param requestUser
     * @param session
     * @return
     */
    @PostMapping("insert")
    public ResponseEntity userInsert(@Valid @RequestBody User requestUser, HttpSession session){

        userService.insertUser(requestUser);
        return new ResponseEntity<>(ResponseMessage.USERREGISTCOMPLETE , HttpStatus.OK);
    }


}
