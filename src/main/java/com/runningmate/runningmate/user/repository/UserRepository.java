package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.User;

import java.util.Optional;


public interface UserRepository {

    Optional<User> findByEmail(String email);

    void save(User user);
}
