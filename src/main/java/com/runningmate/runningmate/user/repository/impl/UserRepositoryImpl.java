package com.runningmate.runningmate.user.repository.impl;

import com.runningmate.runningmate.user.dto.User;
import com.runningmate.runningmate.user.entity.UserInfo;
import com.runningmate.runningmate.user.mapper.UserMapper;
import com.runningmate.runningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public UserInfo selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public void insertUser(UserInfo user) {
        userMapper.insertUser(user);
    }
}
