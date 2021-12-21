package com.runningmate.runningmate.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApplyQuestionValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("질문 미 입력시 프로젝트 신청 실패")
    void failProjectApplyWithoutQuestion() {
        ApplyQuestionUpdateRequestDto request = new ApplyQuestionUpdateRequestDto("");

        Set<ConstraintViolation<ApplyQuestionUpdateRequestDto>> violations = validator.validate(request);
        ConstraintViolation<ApplyQuestionUpdateRequestDto> violation = violations.iterator().next();

        assertEquals("질문을 입력해주세요", violation.getMessage());
    }
}
