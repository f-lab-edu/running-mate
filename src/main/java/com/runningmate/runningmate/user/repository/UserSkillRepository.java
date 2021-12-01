package com.runningmate.runningmate.user.repository;

import com.runningmate.runningmate.user.entity.UserSkill;

import java.util.List;


public interface UserSkillRepository {

    int skillSaveAll(List<UserSkill> userSkill);

}
