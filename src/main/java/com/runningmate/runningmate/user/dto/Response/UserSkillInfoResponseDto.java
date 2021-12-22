package com.runningmate.runningmate.user.dto.Response;

import com.runningmate.runningmate.user.entity.UserSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillInfoResponseDto {

    private long userSkillId;
    private long skillId;
    private String skillName;

    public static UserSkillInfoResponseDto of(UserSkill userSkill) {
        return UserSkillInfoResponseDto.builder()
            .userSkillId(userSkill.getUserSkillId())
            .skillId(userSkill.getSkill().getSkillId())
            .skillName(userSkill.getSkill().getSkillName())
            .build();
    }
}
