package com.runningmate.runningmate.position.domain.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Position {
    private long positionId;
    private String positionName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
