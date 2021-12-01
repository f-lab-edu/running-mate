package com.runningmate.runningmate.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserSkillSaveDto {

    private long userId;

    private long skillId;

}
