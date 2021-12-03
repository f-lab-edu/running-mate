package com.runningmate.runningmate.project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyQuestion {

    private long applyQuestionId;
    private long projectId;
    private String question;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
