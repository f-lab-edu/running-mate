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

    @Transactional(readOnly = true)
    User selsectUserByEmail(String email);

    @Transactional(readOnly = true)
    User selectUserById(long userId);

    void insertUser(User user);

    void updateUser(User user);

    @Transactional(readOnly = true)
    User selectUserByToken(String resetToken);
}
