package com.runningmate.runningmate.user.repository.impl;

import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.mapper.UserMapper;
import com.runningmate.runningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public int save(User user) {
        return userMapper.save(user);
    }
}
