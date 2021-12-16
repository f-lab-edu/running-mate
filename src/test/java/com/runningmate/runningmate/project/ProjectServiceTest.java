package com.runningmate.runningmate.project;

import com.runningmate.runningmate.common.exception.DuplicateApplyException;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.domain.entity.ImageStatus;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.project.domain.entity.ProjectApply;
import com.runningmate.runningmate.project.domain.repository.ApplyAnswerRepository;
import com.runningmate.runningmate.project.domain.repository.ApplyQuestionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectApplyRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectPositionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectSkillRepository;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
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
        when(mybatisProjectApplyRepository.findByUserId(userId)).thenReturn(null);

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

        User user = User.builder()
            .userId(1L)
            .email("test@gmail.com")
            .password("test")
            .nickName("test")
            .build();

        ProjectApply projectApply = ProjectApply.builder().build();

        when(mybatisProjectApplyRepository.findByUserId(userId)).thenReturn(projectApply);

        assertThrows(DuplicateApplyException.class, () -> {
            projectService.projectApply(userId, projectPositionId, projectApplyRequestDto);
        });
    }

}