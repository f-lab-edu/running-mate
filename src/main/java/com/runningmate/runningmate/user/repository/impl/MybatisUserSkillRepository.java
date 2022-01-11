package com.runningmate.runningmate.user.repository.impl;

import com.runningmate.runningmate.user.entity.UserSkill;
import com.runningmate.runningmate.user.mapper.UserSkillMapper;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class MybatisUserSkillRepository implements UserSkillRepository {

    private final UserSkillMapper userSkillMapper;

    @Override
    public void saveAll(List<UserSkill> userSkills) {
        userSkillMapper.insertUserSkills(userSkills);
    }

    @Override
    public void save(UserSkill updateUserSkill) {
        userSkillMapper.updateUserSkill(updateUserSkill);
    }

    @Override
    public void delete(long deleteUserSkillId) {
        userSkillMapper.deleteUserSkill(deleteUserSkillId);
    }
}
