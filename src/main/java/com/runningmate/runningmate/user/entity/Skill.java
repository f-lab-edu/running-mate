package com.runningmate.runningmate.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Skill {

    @NonNull
    private long skillId;

    private String skillName;


}
