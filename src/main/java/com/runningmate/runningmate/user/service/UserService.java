package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;



public interface UserService {

    Optional<User> loginCheck(String email, String password);

    void insertUser(User user);

    void loginUser(HttpSession session, String userEmail);

    void loginout(HttpSession session);
}
