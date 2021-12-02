package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;


public interface UserService {

    UserSaveDto login(UserLoginRequestDto userLoginRequestDto);

    void insertUser(UserSaveDto userSaveDto);

    void loginUser(long userId);

    void logout();
}
