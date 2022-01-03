package com.runningmate.runningmate.project;

import com.runningmate.runningmate.common.exception.DuplicateApplyException;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.domain.entity.ImageStatus;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.project.domain.entity.ApplyQuestion;
import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import com.runningmate.runningmate.project.domain.entity.ProjectPosition;
import com.runningmate.runningmate.project.domain.entity.ProjectSkill;
import com.runningmate.runningmate.project.domain.entity.ProjectStatus;
import com.runningmate.runningmate.project.domain.repository.ApplyAnswerRepository;
import com.runningmate.runningmate.project.domain.repository.ApplyQuestionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectApplyRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectPositionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectSkillRepository;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectUpdateRequestDto;
import com.runningmate.runningmate.project.service.ProjectService;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository mybatisProjectRepository;

    @Mock
    private ProjectPositionRepository mybatisProjectPositionRepository;

    @Mock
    private ProjectSkillRepository mybatisProjectSkillRepository;

    @Mock
    private ApplyQuestionRepository mybatisApplyQuestionRepository;

    @Mock
    private ApplyAnswerRepository mybatisApplyAnswerRepository;

    @Mock
    private ProjectApplyRepository mybatisProjectApplyRepository;

    @Mock
    private UserRepository mybatisUserRepository;

    @Mock
    private ImageUploadService awsS3ImageUploadService;

    @Test
    @DisplayName("프로젝트 목록 조회 성공")
    void successGetProjects() {
        ProjectSearchRequestDto projectSearchRequestDto = ProjectSearchRequestDto.builder()
            .cursor(0)
            .size(10)
            .status(ProjectStatus.RECRUIT)
            .build();

        projectService.getProjects(projectSearchRequestDto);

        verify(mybatisProjectRepository, times(1)).findAll(projectSearchRequestDto);
    }

    @Test
    @DisplayName("프로젝트 조회 성공")
    void successGetProject() {
        projectService.getProject(1L);

        verify(mybatisProjectRepository, times(1)).findByProjectId(1L);
    }

    @Test
    @DisplayName("프로젝트 등록 성공")
    void successCreateProject() {
        List<ProjectPositionSaveRequestDto> positions = new ArrayList<>();
        positions.add(new ProjectPositionSaveRequestDto(1L, 3));
        positions.add(new ProjectPositionSaveRequestDto(2L, 3));

        List<ProjectSkillSaveRequestDto> skills = new ArrayList<>();
        skills.add(new ProjectSkillSaveRequestDto(1L));
        skills.add(new ProjectSkillSaveRequestDto(2L));

        List<ApplyQuestionSaveRequestDto> applyQuestions = new ArrayList<>();
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_1"));
        applyQuestions.add(new ApplyQuestionSaveRequestDto("Question_2"));


        ProjectSaveRequestDto projectSaveRequestDto = ProjectSaveRequestDto.builder()
                .beginDate(LocalDate.now())
                .endDate(LocalDate.now())
                .title("Title")
                .contents("Contents")
                .positions(positions)
                .skills(skills)
                .applyQuestions(applyQuestions)
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("file", "file", "image/jpeg", "file".getBytes());
        User user = User.builder()
            .userId(1L)
            .email("test@gmail.com")
            .password("test")
            .nickName("test")
            .build();

        when(mybatisUserRepository.findByUserId(1L)).thenReturn(Optional.of(user));
        when(awsS3ImageUploadService.upload(multipartFile)).thenReturn(new Image(1L, ImageStatus.BEING_USED, "file", "file", LocalDateTime.now(), LocalDateTime.now()));

        projectService.createProject(1L, projectSaveRequestDto, multipartFile);

        verify(awsS3ImageUploadService, times(1)).upload(any(MultipartFile.class));
        verify(mybatisProjectRepository, times(1)).save(any(Project.class));
        verify(mybatisProjectPositionRepository, times(1)).saveAll(anyList());
        verify(mybatisProjectSkillRepository, times(1)).saveAll(anyList());
        verify(mybatisApplyQuestionRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("프로젝트 수정 성공 - 첨부파일 존재")
    void successProjectModify() {
        long userId = 1L;
        long projectId = 1L;

        MockMultipartFile multipartFile = new MockMultipartFile("file", "file", "image/jpeg", "file".getBytes());

        ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
            .beginDate(LocalDate.of(2021, 12, 01))
            .endDate(LocalDate.of(2021, 12, 31))
            .title("Title")
            .contents("Contents")
            .status(ProjectStatus.RECRUIT)
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .image(Image.builder().imageId(1L).build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);

        projectService.modifyProject(userId, projectId, projectUpdateRequestDto, multipartFile);

        verify(awsS3ImageUploadService, times(1)).delete(project.getImage().getImageId());
        verify(awsS3ImageUploadService, times(1)).upload(multipartFile);
        verify(mybatisProjectRepository, times(1)).update(project);
    }

    @Test
    @DisplayName("프로젝트 수정 성공 - 첨부파일 미존재")
    void successProjectModifyWithoutFile() {
        long userId = 1L;
        long projectId = 1L;

        ProjectUpdateRequestDto projectUpdateRequestDto = ProjectUpdateRequestDto.builder()
            .beginDate(LocalDate.of(2021, 12, 01))
            .endDate(LocalDate.of(2021, 12, 31))
            .title("Title")
            .contents("Contents")
            .status(ProjectStatus.RECRUIT)
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .image(Image.builder().imageId(1L).build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);

        projectService.modifyProject(userId, projectId, projectUpdateRequestDto, null);

        verify(awsS3ImageUploadService, times(0)).delete(project.getImage().getImageId());
        verify(awsS3ImageUploadService, times(0)).upload(null);
        verify(mybatisProjectRepository, times(1)).update(project);
    }

    @Test
    @DisplayName("프로젝트 삭제 실패 - 신청 존재")
    void failProjectDeleteDuplicateApply() {
        long userId = 1L;
        long projectId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);
        when(mybatisProjectApplyRepository.existsByProjectId(projectId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            projectService.deleteProject(userId, projectId);
        });
    }

    @Test
    @DisplayName("프로젝트 삭제 성공")
    void successProjectDelete() {
        long userId = 1L;
        long projectId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);
        when(mybatisProjectApplyRepository.existsByProjectId(projectId)).thenReturn(false);

        projectService.deleteProject(userId, projectId);

        verify(mybatisProjectRepository, times(1)).delete(project);
    }

    @Test
    @DisplayName("프로젝트 포지션 추가 실패 - 이미 등록된 포지션")
    void failProjectPositionAddExistPosition() {
        long userId = 1L;
        long projectId = 1L;
        long positionId = 1L;

        ProjectPositionSaveRequestDto request = new ProjectPositionSaveRequestDto(1L, 3);

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);
        when(mybatisProjectPositionRepository.existsByProjectIdAndPositionId(projectId, positionId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
           projectService.addProjectPosition(userId, projectId, request);
        });
    }

    @Test
    @DisplayName("프로젝트 포지션 추가 성공")
    void successProjectPositionAdd() {
        long userId = 1L;
        long projectId = 1L;

        ProjectPositionSaveRequestDto request = new ProjectPositionSaveRequestDto(1L, 3);

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);
        when(mybatisProjectPositionRepository.existsByProjectIdAndPositionId(projectId, request.getPositionId())).thenReturn(false);

        projectService.addProjectPosition(userId, projectId, request);

        verify(mybatisProjectPositionRepository, times(1)).save(any(ProjectPosition.class));
    }

    @Test
    @DisplayName("프로젝트 포지션 수정 성공")
    void successProjectPositionModify() {
        long userId = 1L;
        long projectId = 1L;
        long projectPositionId = 1L;

        ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto = ProjectPositionUpdateRequestDto.builder()
            .positionId(1L)
            .personnel(3)
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ProjectPosition projectPosition = ProjectPosition.builder()
            .projectPositionId(projectPositionId)
            .project(project)
            .build();

        when(mybatisProjectPositionRepository.findById(projectPositionId)).thenReturn(projectPosition);

        projectService.modifyProjectPosition(userId, projectPositionId, projectPositionUpdateRequestDto);

        verify(mybatisProjectPositionRepository, times(1)).update(projectPosition);
    }

    @Test
    @DisplayName("프로젝트 포지션 삭제 실패 - 신청 존재")
    void failProjectPositionDeleteExistApply() {
        long userId = 1L;
        long projectId = 1L;
        long projectPositionId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ProjectPosition projectPosition = ProjectPosition.builder()
            .projectPositionId(projectPositionId)
            .project(project)
            .build();

        when(mybatisProjectPositionRepository.findById(projectPositionId)).thenReturn(projectPosition);
        when(mybatisProjectApplyRepository.existsByProjectPositionId(projectPositionId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
           projectService.deleteProjectPosition(userId, projectPositionId);
        });
    }

    @Test
    @DisplayName("프로젝트 포지션 삭제 성공")
    void successProjectPositionDelete() {
        long userId = 1L;
        long projectId = 1L;
        long projectPositionId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ProjectPosition projectPosition = ProjectPosition.builder()
            .projectPositionId(projectPositionId)
            .project(project)
            .build();

        when(mybatisProjectPositionRepository.findById(projectPositionId)).thenReturn(projectPosition);
        when(mybatisProjectApplyRepository.existsByProjectPositionId(projectPositionId)).thenReturn(false);

        projectService.deleteProjectPosition(userId, projectPositionId);

        verify(mybatisProjectPositionRepository, times(1)).delete(projectPosition);
    }

    @Test
    @DisplayName("프로젝트 스킬 추가 성공")
    void successProjectSkillAdd() {
        long userId = 1L;
        long projectId = 1L;

        ProjectSkillSaveRequestDto projectSkillSaveRequestDto = ProjectSkillSaveRequestDto.builder()
            .skillId(1L)
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);

        projectService.addProjectSKill(userId, projectId, projectSkillSaveRequestDto);

        verify(mybatisProjectSkillRepository, times(1)).save(any(ProjectSkill.class));
    }

    @Test
    @DisplayName("프로젝트 스킬 삭제 성공")
    void successProjectSkillDelete() {
        long userId = 1L;
        long projectId = 1L;
        long projectSkillId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ProjectSkill projectSkill = ProjectSkill.builder()
            .projectSkillId(projectSkillId)
            .project(project)
            .build();

        when(mybatisProjectSkillRepository.findByProjectSkillId(projectSkillId)).thenReturn(projectSkill);

        projectService.deleteProjectSKill(userId, projectSkillId);

        verify(mybatisProjectSkillRepository, times(1)).delete(projectSkill);
    }

    @Test
    @DisplayName("프로젝트 신청 성공")
    void successProjectApply() {
        long userId = 1L;
        long projectPositionId = 1L;

        List<ProjectApplyRequestDto> projectApplyRequestDto = new ArrayList<>();
        projectApplyRequestDto.add(new ProjectApplyRequestDto(1L, "ANSWER_1"));
        projectApplyRequestDto.add(new ProjectApplyRequestDto(2L, "ANSWER_2"));

        User user = User.builder()
            .userId(1L)
            .email("test@gmail.com")
            .password("test")
            .nickName("test")
            .build();

        when(mybatisUserRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(mybatisProjectApplyRepository.existsProjectPositionIdAndUserId(projectPositionId, userId)).thenReturn(false);

        projectService.projectApply(userId, projectPositionId, projectApplyRequestDto);

        verify(mybatisProjectApplyRepository, times(1)).save(any(ProjectApply.class));
        verify(mybatisApplyAnswerRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("프로젝트 신청 실패 - 중복 신청")
    void failProjectApplyDuplicateApply() {
        long userId = 1L;
        long projectPositionId = 1L;

        List<ProjectApplyRequestDto> projectApplyRequestDto = new ArrayList<>();
        projectApplyRequestDto.add(new ProjectApplyRequestDto(1L, "ANSWER_1"));
        projectApplyRequestDto.add(new ProjectApplyRequestDto(2L, "ANSWER_2"));

        when(mybatisProjectApplyRepository.existsProjectPositionIdAndUserId(projectPositionId, userId)).thenReturn(true);

        assertThrows(DuplicateApplyException.class, () -> {
            projectService.projectApply(userId, projectPositionId, projectApplyRequestDto);
        });
    }

    @Test
    @DisplayName("프로젝트 신청 질문 추가 성공")
    void successApplyQuestionAdd() {
        long userId = 1L;
        long projectId = 1L;

        ApplyQuestionSaveRequestDto applyQuestionSaveRequestDto = ApplyQuestionSaveRequestDto.builder()
            .question("Question")
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        when(mybatisProjectRepository.findByProjectId(projectId)).thenReturn(project);

        projectService.addApplyQuestion(userId, projectId, applyQuestionSaveRequestDto);

        verify(mybatisApplyQuestionRepository, times(1)).save(any(ApplyQuestion.class));
    }

    @Test
    @DisplayName("프로젝트 신청 질문 수정 성공")
    void successApplyQuestionModify() {
        long userId = 1L;
        long projectId = 1L;
        long applyQuestionId = 1L;

        ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto = ApplyQuestionUpdateRequestDto.builder()
            .question("Question")
            .build();

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ApplyQuestion applyQuestion = ApplyQuestion.builder()
            .applyQuestionId(applyQuestionId)
            .project(project)
            .build();

        when(mybatisApplyQuestionRepository.findByApplyQuestionId(applyQuestionId)).thenReturn(applyQuestion);

        projectService.modifyApplyQuestion(userId, applyQuestionId, applyQuestionUpdateRequestDto);

        verify(mybatisApplyQuestionRepository, times(1)).update(any(ApplyQuestion.class));
    }

    @Test
    @DisplayName("프로젝트 신청 질문 삭제 성공")
    void successApplyQuestionDelete() {
        long userId = 1L;
        long projectId = 1L;
        long applyQuestionId = 1L;

        Project project = Project.builder()
            .projectId(projectId)
            .leader(User.builder()
                .userId(userId)
                .email("test@gmail.com")
                .password("test")
                .nickName("tester")
                .build())
            .build();

        ApplyQuestion applyQuestion = ApplyQuestion.builder()
            .applyQuestionId(applyQuestionId)
            .project(project)
            .build();

        when(mybatisApplyQuestionRepository.findByApplyQuestionId(applyQuestionId)).thenReturn(applyQuestion);

        projectService.deleteApplyQuestion(userId, applyQuestionId);

        verify(mybatisApplyQuestionRepository, times(1)).delete(any(ApplyQuestion.class));
    }
}