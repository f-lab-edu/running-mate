package com.runningmate.runningmate.project.service;

import com.runningmate.runningmate.common.exception.DuplicateApplyException;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.project.domain.entity.*;
import com.runningmate.runningmate.project.domain.repository.ApplyAnswerRepository;
import com.runningmate.runningmate.project.domain.repository.ApplyQuestionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectApplyRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectPositionRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectSkillRepository;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.skill.domain.entity.Skill;
import com.runningmate.runningmate.user.entity.User;
import java.util.List;
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
    private final ApplyAnswerRepository mybatisApplyAnswerRepository;
    private final ProjectApplyRepository mybatisProjectApplyRepository;

    private final ImageUploadService awsS3ImageUploadService;

    @Transactional(readOnly = true)
    public Project getProject(long projectId) {
        return mybatisProjectRepository.findByProjectId(projectId);
    }

    @Transactional
    public void createProject(long userId, ProjectSaveRequestDto projectSaveRequestDto, MultipartFile multipartFile) {
        Image image = awsS3ImageUploadService.upload(multipartFile);

        Project project = Project.builder()
            .leader(User.builder().userId(userId).build())
            .beginDate(projectSaveRequestDto.getBeginDate())
            .endDate(projectSaveRequestDto.getEndDate())
            .title(projectSaveRequestDto.getTitle())
            .contents(projectSaveRequestDto.getContents())
            .status(RECRUIT)
            .image(image)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

        mybatisProjectRepository.save(project);

        mybatisProjectPositionRepository.saveAll(projectSaveRequestDto.getPositions().stream()
            .map(projectPosition -> ProjectPosition.builder()
                .project(project)
                .position(Position.builder().positionId(projectPosition.getPositionId()).build())
                .personnel(projectPosition.getPersonnel())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));

        mybatisProjectSkillRepository.saveAll(projectSaveRequestDto.getSkills().stream()
            .map(projectSkill -> ProjectSkill.builder()
                .project(project)
                .skill(Skill.builder().skillId(projectSkill.getSkillId()).build())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));

        mybatisApplyQuestionRepository.saveAll(projectSaveRequestDto.getApplyQuestions().stream()
            .map(applyQuestion -> ApplyQuestion.builder()
                .project(project)
                .question(applyQuestion.getQuestion())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));
    }

    @Transactional
    public void projectApply(long userId, long projectPositionId, List<ProjectApplyRequestDto> projectApplyRequestDto) {
        ProjectApply existProjectApply = mybatisProjectApplyRepository.findByUserId(userId);

        if(existProjectApply != null) {
            throw new DuplicateApplyException("이미 신청한 프로젝트 입니다.");
        }

        ProjectApply projectApply = ProjectApply.builder()
            .projectPosition(ProjectPosition.builder().projectPositionId(projectPositionId).build())
            .user(User.builder().userId(userId).build())
            .status(ProjectApplyStatus.WAIT)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

        mybatisProjectApplyRepository.save(projectApply);

        mybatisApplyAnswerRepository.saveAll(projectApplyRequestDto.stream()
            .map(applyRequest -> ApplyAnswer.builder()
                .projectApply(projectApply)
                .applyQuestion(ApplyQuestion.builder().applyQuestionId(applyRequest.getApplyQuestionId()).build())
                .answer(applyRequest.getAnswer())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList()));
    }
}
