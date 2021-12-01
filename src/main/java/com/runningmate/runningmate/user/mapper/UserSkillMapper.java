package com.runningmate.runningmate.user.mapper;

import com.runningmate.runningmate.user.entity.UserSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 유저스킬 관련 mapper
 *
 * @author junsoo
 */
@Mapper
public interface UserSkillMapper {

    int skillSaveAll(List<UserSkill> userSkill);
}
