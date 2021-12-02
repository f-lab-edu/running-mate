package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserSaveDto;


public interface UserService {

    UserSaveDto login(String email, String password);

    void insertUser(UserSaveDto userSaveDto);

    void loginUser(long userId);

    void logout();
}
