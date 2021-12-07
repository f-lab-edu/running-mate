package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserMapper {

    Optional<User> findByEmail(String email);

    void save(User user);
}
