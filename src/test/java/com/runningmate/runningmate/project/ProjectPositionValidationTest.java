package com.runningmate.runningmate.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProjectPositionValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("포지션 아이디 미 입력시 프로젝트 포지션 추가 실패")
    void addProjectPositionWithoutPositionId() {
        ProjectPositionSaveRequestDto request = new ProjectPositionSaveRequestDto(0, 3);

        Set<ConstraintViolation<ProjectPositionSaveRequestDto>> violations = validator.validate(request);
        ConstraintViolation<ProjectPositionSaveRequestDto> violation = violations.iterator().next();

        assertEquals("포지션을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("정원 미 입력시 프로젝트 포지션 추가 실패")
    void addProjectPositionWithoutPersonnel() {
        ProjectPositionSaveRequestDto request = new ProjectPositionSaveRequestDto(1, 0);

        Set<ConstraintViolation<ProjectPositionSaveRequestDto>> violations = validator.validate(request);
        ConstraintViolation<ProjectPositionSaveRequestDto> violation = violations.iterator().next();

        assertEquals("포지션별 인원은 최소 1명 이상 이어야 합니다", violation.getMessage());
    }

    @Test
    @DisplayName("포지션 아이디 미 입력시 프로젝트 포지션 수정 실패")
    void modifyProjectPositionWithoutPositionId() {
        ProjectPositionUpdateRequestDto request = new ProjectPositionUpdateRequestDto(0, 3);

        Set<ConstraintViolation<ProjectPositionUpdateRequestDto>> violations = validator.validate(request);
        ConstraintViolation<ProjectPositionUpdateRequestDto> violation = violations.iterator().next();

        assertEquals("포지션을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("정원 미 입력시 프로젝트 포지션 수정 실패")
    void modifyProjectPositionWithoutPersonnel() {
        ProjectPositionUpdateRequestDto request = new ProjectPositionUpdateRequestDto(1, 0);

        Set<ConstraintViolation<ProjectPositionUpdateRequestDto>> violations = validator.validate(request);
        ConstraintViolation<ProjectPositionUpdateRequestDto> violation = violations.iterator().next();

        assertEquals("포지션별 인원은 최소 1명 이상 이어야 합니다", violation.getMessage());
    }
}
