package com.runningmate.runningmate.user.aop;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.entity.User;
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

    @Before("@annotation(com.runningmate.runningmate.user.aop.LoginCheck) && @annotation(loginCheck)")
    public void loginCheck(LoginCheck loginCheck) throws HttpClientErrorException {

        long loginUserId = SessionUtils.getLoginSessionUserId();

        if(loginUserId <= 0){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        loginCheck(loginUserId, loginCheck.userLevel());
    }
    private void loginCheck(long loginUserId, UserLevel userLevel) throws HttpClientErrorException {
        LoginCheck.UserLevel level = userService.findByUserId(loginUserId).get().getLevel();
        if(level == null || level != userLevel){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
