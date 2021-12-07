package com.runningmate.runningmate.project.service;

import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.project.domain.entity.*;
import com.runningmate.runningmate.project.domain.repository.ApplyQuestionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectPositionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectSkillRepository;
import com.runningmate.runningmate.project.dto.ProjectSaveRequestDto;
import com.runningmate.runningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.runningmate.runningmate.project.domain.entity.ProjectStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository mybatisProjectRepository;
    private final ProjectPositionRepository mybatisProjectPositionRepository;
    private final ProjectSkillRepository mybatisProjectSkillRepository;
    private final ApplyQuestionRepository mybatisApplyQuestionRepository;
    private final UserRepository mybatisUserRepository;

    private final ImageUploadService awsS3ImageUploadService;

    @Transactional
    public void createProject(long userId, ProjectSaveRequestDto projectSaveRequestDto, MultipartFile multipartFile) {
        Image image = awsS3ImageUploadService.upload(multipartFile);

        Project project = Project.builder()
            .leader(userId)
            .beginDate(projectSaveRequestDto.getBeginDate())
            .endDate(projectSaveRequestDto.getEndDate())
            .title(projectSaveRequestDto.getTitle())
            .contents(projectSaveRequestDto.getContents())
            .status(RECRUIT)
            .imageId(image.getImageId())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

        mybatisProjectRepository.save(project);

        mybatisProjectPositionRepository.saveAll(projectSaveRequestDto.getPositions().stream()
            .map(projectPosition -> ProjectPosition.builder()
                .projectId(project.getProjectId())
                .positionId(projectPosition.getPositionId())
                .personnel(projectPosition.getPersonnel())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));

        mybatisProjectSkillRepository.saveAll(projectSaveRequestDto.getSkills().stream()
            .map(projectSkill -> ProjectSkill.builder()
                .projectId(project.getProjectId())
                .skillId(projectSkill.getSkillId())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));

        mybatisApplyQuestionRepository.saveAll(projectSaveRequestDto.getApplyQuestions().stream()
            .map(applyQuestion -> ApplyQuestion.builder()
                .projectId(project.getProjectId())
                .question(applyQuestion.getQuestion())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));
    }
}
