package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;


/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserMapper {

    User selsectUserByEmail(String email);

    User selectUserById(long userId);

    void insertUser(User user);

    void updateUser(User user);

    User selectUserByToken(String resetToken);
}
