package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserMapper {

    UserInfo selectUserByEmail(String email);

    void insertUser(UserInfo user);
}
