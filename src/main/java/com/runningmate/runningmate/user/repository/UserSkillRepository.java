package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.UserSkill;
import java.util.List;


public interface UserSkillRepository {

    void saveAll(List<UserSkill> userSkills);

    void save(UserSkill updateUserSkill);

    void delete(long deleteUserSkillId);
}
