package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.User;
import java.util.Optional;


public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(long userId);

    void save(User user);

    void update(User user);

    Optional<User> getUserByResetToken(String resetToken);
}
