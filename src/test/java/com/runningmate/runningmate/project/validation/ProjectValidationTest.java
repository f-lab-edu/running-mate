package com.runningmate.runningmate.project.validation;

import com.runningmate.runningmate.common.utils.ValidList;
import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("프로젝트 등록 시 Validation Test")
    class CreateProject {
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

    @Nested
    @DisplayName("프로젝트 수정 시 Validation Test")
    class ModifyProject {

        @Test
        @DisplayName("시작일 미 입력시 수정 실패")
        void modifyProjectWithoutBeginDate() {
            ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Title")
                .contents("Contents")
                .status(ProjectStatus.RECRUIT)
                .build();

            Set<ConstraintViolation<ProjectUpdateRequestDto>> violations = validator.validate(projectUpdateRequestDto);
            ConstraintViolation<ProjectUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트 시작일을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("종료일 미 입력시 수정 실패")
        void modifyProjectWithoutEndDate() {
            ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .title("Title")
                .contents("Contents")
                .status(ProjectStatus.RECRUIT)
                .build();

            Set<ConstraintViolation<ProjectUpdateRequestDto>> violations = validator.validate(projectUpdateRequestDto);
            ConstraintViolation<ProjectUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트 종료일을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("제목 미 입력시 수정 실패")
        void modifyProjectWithoutTitle() {
            ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .contents("Contents")
                .status(ProjectStatus.RECRUIT)
                .build();

            Set<ConstraintViolation<ProjectUpdateRequestDto>> violations = validator.validate(projectUpdateRequestDto);
            ConstraintViolation<ProjectUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트 제목을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("내용 미 입력시 수정 실패")
        void modifyProjectWithoutContents() {
            ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Title")
                .status(ProjectStatus.RECRUIT)
                .build();

            Set<ConstraintViolation<ProjectUpdateRequestDto>> violations = validator.validate(projectUpdateRequestDto);
            ConstraintViolation<ProjectUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트 내용을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("진행상태 미 입력시 수정 실패")
        void modifyProjectWithoutStatus() {
            ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
                .beginDate(LocalDate.of(2021, 12, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .title("Title")
                .contents("Contents")
                .build();

            Set<ConstraintViolation<ProjectUpdateRequestDto>> violations = validator.validate(projectUpdateRequestDto);
            ConstraintViolation<ProjectUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트 진행상태를 선택해주세요", violation.getMessage());
        }
    }

    @Nested
    @DisplayName("프로젝트 포지션 수정 시 Validation Test")
    class ModifyProjectPosition {

        @Test
        @DisplayName("포지션 미 입력 시 추가 실패")
        void addProjectPositionWithoutPositionId() {
            ProjectPositionSaveRequestDto projectPositionSaveRequestDto = ProjectPositionSaveRequestDto
                .builder()
                .positionId(0)
                .personnel(3)
                .build();

            Set<ConstraintViolation<ProjectPositionSaveRequestDto>> violations = validator.validate(projectPositionSaveRequestDto);
            ConstraintViolation<ProjectPositionSaveRequestDto> violation = violations.iterator().next();

            assertEquals("포지션을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("정원 미 입력 시 추가 실패")
        void addProjectPositionWithoutPersonnel() {
            ProjectPositionSaveRequestDto projectPositionSaveRequestDto = ProjectPositionSaveRequestDto
                .builder()
                .positionId(1)
                .personnel(0)
                .build();

            Set<ConstraintViolation<ProjectPositionSaveRequestDto>> violations = validator.validate(projectPositionSaveRequestDto);
            ConstraintViolation<ProjectPositionSaveRequestDto> violation = violations.iterator().next();

            assertEquals("포지션별 인원은 최소 1명 이상 이어야 합니다", violation.getMessage());
        }

        @Test
        @DisplayName("추가 요청 데이터 정상 입력 시 통과")
        void addProjectPosition() {
            ProjectPositionSaveRequestDto projectPositionSaveRequestDto = ProjectPositionSaveRequestDto
                .builder()
                .positionId(1)
                .personnel(3)
                .build();

            Set<ConstraintViolation<ProjectPositionSaveRequestDto>> violations = validator.validate(projectPositionSaveRequestDto);

            assertEquals(0, violations.size());
        }

        @Test
        @DisplayName("포지션 미 입력 시 수정 실패")
        void modifyProjectPositionWithoutPositionId() {
            ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto = ProjectPositionUpdateRequestDto
                .builder()
                .positionId(0)
                .personnel(3)
                .build();

            Set<ConstraintViolation<ProjectPositionUpdateRequestDto>> violations = validator.validate(projectPositionUpdateRequestDto);
            ConstraintViolation<ProjectPositionUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("포지션을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("정원 미 입력 시 수정 실패")
        void modifyProjectPositionWithoutPersonnel() {
            ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto = ProjectPositionUpdateRequestDto
                .builder()
                .positionId(1)
                .personnel(0)
                .build();

            Set<ConstraintViolation<ProjectPositionUpdateRequestDto>> violations = validator.validate(projectPositionUpdateRequestDto);
            ConstraintViolation<ProjectPositionUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("포지션별 인원은 최소 1명 이상 이어야 합니다", violation.getMessage());
        }

        @Test
        @DisplayName("수정 요청 데이터 정상 입력 시 통과")
        void modifyProjectPosition() {
            ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto = ProjectPositionUpdateRequestDto
                .builder()
                .positionId(1)
                .personnel(3)
                .build();

            Set<ConstraintViolation<ProjectPositionUpdateRequestDto>> violations = validator.validate(projectPositionUpdateRequestDto);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("프로젝트 스킬 수정 시 Validation Test")
    class ModifyProjectSkill {

        @Test
        @DisplayName("스킬 미 입력 시 추가 실패")
        void addProjectSkillWithoutSkillId() {
            ProjectSkillSaveRequestDto projectSkillSaveRequestDto = ProjectSkillSaveRequestDto.builder()
                .skillId(0)
                .build();

            Set<ConstraintViolation<ProjectSkillSaveRequestDto>> violations = validator.validate(projectSkillSaveRequestDto);
            ConstraintViolation<ProjectSkillSaveRequestDto> violation = violations.iterator().next();

            assertEquals("프로젝트에서 사용할 스킬을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("추가 요청 데이터 정상 입력 시 통과")
        void addProjectSkill() {
            ProjectSkillSaveRequestDto projectSkillSaveRequestDto = ProjectSkillSaveRequestDto.builder()
                .skillId(1)
                .build();

            Set<ConstraintViolation<ProjectSkillSaveRequestDto>> violations = validator.validate(projectSkillSaveRequestDto);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("프로젝트 신청 질문 수정 시 Validation Test")
    class ModifyApplyQuestion {

        @Test
        @DisplayName("질문 미 입력시 추가 실패")
        void addApplyQuestionWithoutQuestion() {
            ApplyQuestionSaveRequestDto applyQuestionSaveRequestDto = ApplyQuestionSaveRequestDto.builder().build();

            Set<ConstraintViolation<ApplyQuestionSaveRequestDto>> violations = validator.validate(applyQuestionSaveRequestDto);
            ConstraintViolation<ApplyQuestionSaveRequestDto> violation = violations.iterator().next();

            assertEquals("질문을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("추가 요청 데이터 정상 입력 시 통과")
        void addApplyQuestion() {
            ApplyQuestionSaveRequestDto applyQuestionSaveRequestDto = ApplyQuestionSaveRequestDto.builder()
                .question("Question")
                .build();

            Set<ConstraintViolation<ApplyQuestionSaveRequestDto>> violations = validator.validate(applyQuestionSaveRequestDto);

            assertEquals(0, violations.size());
        }

        @Test
        @DisplayName("질문 미 입력시 수정 실패")
        void modifyApplyQuestionWithoutQuestion() {
            ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto = ApplyQuestionUpdateRequestDto.builder().build();

            Set<ConstraintViolation<ApplyQuestionUpdateRequestDto>> violations = validator.validate(applyQuestionUpdateRequestDto);
            ConstraintViolation<ApplyQuestionUpdateRequestDto> violation = violations.iterator().next();

            assertEquals("질문을 입력해주세요", violation.getMessage());
        }

        @Test
        @DisplayName("수정 요청 데이터 정상 입력 시 통과")
        void modifyApplyQuestion() {
            ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto = ApplyQuestionUpdateRequestDto.builder()
                .question("Question")
                .build();

            Set<ConstraintViolation<ApplyQuestionUpdateRequestDto>> violations = validator.validate(applyQuestionUpdateRequestDto);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("프로젝트 신청 시 Validation Test")
    class ProjectApply {
        @Test
        @DisplayName("프로젝트 신청 시 질문 아이디 미 입력시 등록 실패")
        void projectApplyWithoutApplyQuestionId() {
            List<ProjectApplyRequestDto> projectApplyRequestDto = new ValidList<>();
            projectApplyRequestDto.add(ProjectApplyRequestDto.builder()
                .applyQuestionId(0)
                .answer("ANSWER")
                .build());

            Set<ConstraintViolation<List<ProjectApplyRequestDto>>> violations = validator.validate(projectApplyRequestDto);
            ConstraintViolation<List<ProjectApplyRequestDto>> violation = violations.iterator().next();

            assertEquals("질문 아이디는 필수 입니다.", violation.getMessage());
        }

        @Test
        @DisplayName("프로젝트 신청 시 답변 미 입력시 등록 실패")
        void projectApplyWithoutAnswer() {
            List<ProjectApplyRequestDto> projectApplyRequestDto = new ValidList<>();
            projectApplyRequestDto.add(ProjectApplyRequestDto.builder()
                .applyQuestionId(1)
                .build());

            Set<ConstraintViolation<List<ProjectApplyRequestDto>>> violations = validator.validate(projectApplyRequestDto);
            ConstraintViolation<List<ProjectApplyRequestDto>> violation = violations.iterator().next();

            assertEquals("답변을 입력해주세요.", violation.getMessage());
        }
    }
}
