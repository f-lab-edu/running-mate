package com.runningmate.runningmate.comment.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.runningmate.runningmate.comment.dto.request.CommentSaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CommentValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("댓글 등록 시 Validation Test")
    class CreateTest {
        @Test
        @DisplayName("내용 미 입력 시 댓글 등록 실패")
        void createCommentWithoutContents() {
            CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .contents(null)
                .build();

            Set<ConstraintViolation<CommentSaveRequestDto>> violations = validator.validate(commentSaveRequestDto);
            ConstraintViolation<CommentSaveRequestDto> violation = violations.iterator().next();

            assertEquals("내용을 입력해주세요.", violation.getMessage());
        }

        @Test
        @DisplayName("허용 글자 수 초과 시 댓글 등록 실패")
        void createCommentWithoutContentsLength() {
            CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .contents("A".repeat(251))
                .build();

            Set<ConstraintViolation<CommentSaveRequestDto>> violations = validator.validate(commentSaveRequestDto);
            ConstraintViolation<CommentSaveRequestDto> violation = violations.iterator().next();

            assertEquals("250자 이상 작성할 수 없습니다.", violation.getMessage());
        }

        @Test
        @DisplayName("정상 입력 시 댓글 등록 성공")
        void createComment() {
            CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .contents("Contents")
                .build();

            Set<ConstraintViolation<CommentSaveRequestDto>> violations = validator.validate(commentSaveRequestDto);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("댓글 수정 시 Validation Test")
    class ModifyTest {
        @Test
        @DisplayName("내용 미 입력 시 댓글 수정 실패")
        void modifyCommentWithoutContents() {
            CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .contents(null)
                .build();

            Set<ConstraintViolation<CommentUpdateRequestDto>> violations = validator.validate(commentUpdateRequestDto);
            ConstraintViolation<CommentUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("내용을 입력해주세요.", violation.getMessage());
        }

        @Test
        @DisplayName("허용 글자 수 초과 시 댓글 수정 실패")
        void modifyCommentWithoutContentsLength() {
            CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .contents("A".repeat(251))
                .build();

            Set<ConstraintViolation<CommentUpdateRequestDto>> violations = validator.validate(commentUpdateRequestDto);
            ConstraintViolation<CommentUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("250자 이상 작성할 수 없습니다.", violation.getMessage());
        }

        @Test
        @DisplayName("정상 입력 시 댓글 수정 성공")
        void modifyComment() {
            CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .contents("Contents")
                .build();

            Set<ConstraintViolation<CommentUpdateRequestDto>> violations = validator.validate(commentUpdateRequestDto);

            assertEquals(0, violations.size());
        }
    }
}
