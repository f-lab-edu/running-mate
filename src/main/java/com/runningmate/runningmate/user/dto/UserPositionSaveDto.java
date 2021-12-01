package com.runningmate.runningmate.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPositionSaveDto {

    private long positionId;

    private String positionName;

}
