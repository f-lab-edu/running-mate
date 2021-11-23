package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserMapper {

    User selectUserByEmail(String email);

    void insertUser(User user);
}
