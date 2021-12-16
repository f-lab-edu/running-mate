package com.runningmate.runningmate.user.repository.impl;

import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.mapper.UserMapper;
import com.runningmate.runningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMapper.selsectUserByEmail(email));
    }

    @Override
    public Optional<User> findByUserId(long userId) {
        return Optional.ofNullable(userMapper.selectUserById(userId));
    }

    @Override
    public void save(User user) {
        userMapper.insertUser(user);
    }
}
