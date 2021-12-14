package com.runningmate.runningmate.user.dto;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSkillSaveReqeustDto {
    @Min(value = 1, message = "유저 스킬에 저장하려면 유저정보가 필요합니다.")
    private long userId;

    @Min(value = 1, message = "유저 스킬에 넣을 스킬을 하나 이상 있어야 합니다")
    private long skillId;
}
