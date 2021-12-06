package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;


public interface UserService {

    User login(UserLoginRequestDto userLoginRequestDto);

    void insertUser(UserSaveDto userSaveDto);

    void loginUser(long userId);

    void logout();
}
