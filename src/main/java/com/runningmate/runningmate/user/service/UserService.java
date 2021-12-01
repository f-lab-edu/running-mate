package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;

import java.util.Optional;


public interface UserService {

    void insertUser(UserSaveDto userSaveDto);

    Optional<User> findByEmail(String email);
}
