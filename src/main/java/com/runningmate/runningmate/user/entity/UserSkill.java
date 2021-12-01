package com.runningmate.runningmate.user.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {

    @NonNull
    private long userSkillId;

    @NonNull
    private long userId;

    @NonNull
    private long skillId;
}
