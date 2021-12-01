package com.runningmate.runningmate.user.repository.impl;

import com.runningmate.runningmate.user.entity.UserSkill;
import com.runningmate.runningmate.user.mapper.UserSkillMapper;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MybatisUserSkillRepository implements UserSkillRepository {

    private final UserSkillMapper userSkillMapper;

    @Override
    public int skillSaveAll(List<UserSkill> userSkill) {
        return userSkillMapper.skillSaveAll(userSkill);
    }
}
