package com.runningmate.runningmate.project;

import com.runningmate.runningmate.project.dto.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.dto.ProjectSkillSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("프로젝트 시작일 미 입력시 프로젝트 등록 실패")
    void createProjectWithoutBeginDate() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("프로젝트 시작일을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 종료일 미 입력시 프로젝트 등록 실패")
    void createProjectWithoutEndDate() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("프로젝트 종료일을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 제목 미 입력시 프로젝트 등록 실패")
    void createProjectWithoutTitle() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("프로젝트 제목을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 내용 미 입력시 프로젝트 등록 실패")
    void createProjectWithoutContents() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("프로젝트 내용을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 포지션 리스트 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutPositionList() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("1개 이상의 포지션이 등록되어야 합니다", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 포지션 리스트 내 포지션 아이디 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutPositionListInPositionId() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(0, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("포지션을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 포지션 리스트 내 정원 아이디 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutPositionListInPersonnel() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 0));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("포지션별 인원은 최소 1명 이상 이어야 합니다", violation.getMessage());
    }


    @Test
    @DisplayName("프로젝트 스킬 리스트 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutSkillList() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("1개 이상의 스킬이 등록되어야 합니다", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 스킬 리스트 내 스킬 아이디 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutSkillListInSkillId() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto());

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("프로젝트에서 사용할 스킬을 입력해주세요", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 질문 리스트 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutApplyQuestionList() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("1개 이상의 질문이 등록되어야 합니다", violation.getMessage());
    }

    @Test
    @DisplayName("프로젝트 질문 리스트 내 질문 미 등록시 프로젝트 등록 실패")
    void createProjectWithoutApplyQuestionListInQuestion() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto());

        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Project Title")
                .contents("Project Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        Set<ConstraintViolation<ProjectSaveRequestDto>> violations = validator.validate(projectSaveRequestDto);
        ConstraintViolation<ProjectSaveRequestDto> violation = violations.iterator().next();

        assertEquals("질문을 입력해주세요", violation.getMessage());
    }
}
