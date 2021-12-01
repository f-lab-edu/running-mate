package com.runningmate.runningmate.aop;

import com.runningmate.runningmate.common.utils.ResponseMessage;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;


@Aspect
@RequiredArgsConstructor
@Component
public class LoginCheckAspect {

    private final UserService userService;

    @Before("@annotation(com.runningmate.runningmate.aop.LoginCheck) && @annotation(loginCheck)")
    public void loginCheck(LoginCheck loginCheck) throws HttpClientErrorException {

        String loginUserEmail = (String) SessionUtils.getSession("LOGIN_USER_EMAIL");

        if(loginUserEmail == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, ResponseMessage.REQUEST_LOGIN);
        }
        if(loginCheck.userLevel() == LoginCheck.UserLevel.MANAGER) {
            managerLoginCheck(loginUserEmail);
        }
    }

    private void managerLoginCheck(String loginUserEmail) throws HttpClientErrorException {
        LoginCheck.UserLevel level = userService.findByEmail(loginUserEmail).get().getLevel();
        if(level == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, ResponseMessage.MANAGER_ONLY);
        }
    }

}
