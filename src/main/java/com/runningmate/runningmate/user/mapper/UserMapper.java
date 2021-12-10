package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserMapper {

    User findByEmail(String email);

    User findByUserId(long userId);

    void save(User user);
}
