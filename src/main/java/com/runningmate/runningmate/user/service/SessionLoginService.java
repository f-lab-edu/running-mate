package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.entity.User;
import java.util.Optional;


public interface SessionLoginService {

    Optional<User> login(UserLoginRequestDto userLoginRequestDto);

    void loginUser(long userId);

    void logout();
}