package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserSaveDto;


public interface LoginService {

    UserSaveDto loginCheck(String email, String password);

    void loginUser(String userEmail);

    void logout();

}
