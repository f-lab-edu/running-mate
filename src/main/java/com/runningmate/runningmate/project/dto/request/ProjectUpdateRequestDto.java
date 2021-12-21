package com.runningmate.runningmate.project.dto.request;

import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequestDto {

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

    @NotNull(message = "프로젝트 진행상태를 선택해주세요")
    private ProjectStatus status;
}
