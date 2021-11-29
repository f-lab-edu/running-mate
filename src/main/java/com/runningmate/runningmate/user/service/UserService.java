package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserSaveDto;

import javax.servlet.http.HttpSession;


public interface UserService {

    UserSaveDto loginCheck(String email, String password);

    void insertUser(UserSaveDto userSaveDto);

    void loginUser(HttpSession session, String userEmail);

    void logout(HttpSession session);
}
