package com.runningmate.runningmate.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPositionSaveRequestDto {
    @Min(value = 1, message = "포지션을 입력해주세요")
    private long positionId;

    @Min(value = 1, message = "포지션별 인원은 최소 1명 이상 이어야 합니다")
    private int personnel;
}
