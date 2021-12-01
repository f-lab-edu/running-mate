package com.runningmate.runningmate.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Position {

    @NonNull
    private long positionId;

    private String positionName;
}
