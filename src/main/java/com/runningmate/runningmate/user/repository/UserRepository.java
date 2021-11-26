package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.User;


public interface UserRepository {

    User findByEmail(String email);

    void save(User user);

}
