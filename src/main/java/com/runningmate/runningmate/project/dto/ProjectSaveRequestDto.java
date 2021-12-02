package com.runningmate.runningmate.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSaveRequestDto {

    @NotNull(message = "프로젝트 시작일을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @NotNull(message = "프로젝트 종료일을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "프로젝트 제목을 입력해주세요")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "프로젝트 내용을 입력해주세요")
    private String contents;

    @NotNull
    @Size(min = 1, message = "1개 이상의 포지션이 등록되어야 합니다")
    @Valid
    private List<ProjectPositionSaveRequestDto> positions;

    @NotNull
    @Size(min = 1, message = "1개 이상의 스킬이 등록되어야 합니다")
    @Valid
    private List<ProjectSkillSaveRequestDto> skills;

    @NotNull
    @Size(min = 1, message = "1개 이상의 질문이 등록되어야 합니다")
    @Valid
    private List<ApplyQuestionSaveRequestDto> applyQuestions;
}
