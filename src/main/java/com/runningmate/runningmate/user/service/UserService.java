package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;

import java.util.Optional;


public interface UserService {

    Optional<User> login(UserLoginRequestDto userLoginRequestDto);

    void insertUser(UserSaveDto userSaveDto);

    void loginUser(long userId);

    void logout();
}
