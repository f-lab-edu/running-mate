package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.UserInfo;


public interface UserRepository {

    UserInfo selectUserByEmail(String email);

    void insertUser(UserInfo user);

}
