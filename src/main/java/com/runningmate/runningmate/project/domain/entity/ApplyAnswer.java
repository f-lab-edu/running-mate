package com.runningmate.runningmate.project.domain.entity;

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
public class ApplyAnswer {

    private long applyAnswerId;
    private ApplyQuestion applyQuestion;
    private ProjectApply projectApply;
    private String answer;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
