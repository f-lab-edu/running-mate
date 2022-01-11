package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.UserSkill;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * User 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserSkillMapper {

    void insertUserSkills(List<UserSkill> userSkills);

    void updateUserSkill(UserSkill updateUserSkill);

    void deleteUserSkill(long deleteUserSkillId);
}
