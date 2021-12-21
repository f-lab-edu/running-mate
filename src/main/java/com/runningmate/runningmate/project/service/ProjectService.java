package com.runningmate.runningmate.project.service;

import com.runningmate.runningmate.common.exception.DuplicateApplyException;
import com.runningmate.runningmate.common.exception.NonCreatorException;
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
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectUpdateRequestDto;
import com.runningmate.runningmate.skill.domain.entity.Skill;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final UserRepository mybatisUserRepository;

    private final ImageUploadService awsS3ImageUploadService;

    @Transactional(readOnly = true)
    public List<Project> getProjects(ProjectSearchRequestDto projectSearchRequestDto) {
        return mybatisProjectRepository.findAll(projectSearchRequestDto);
    }

    @Transactional(readOnly = true)
    public Project getProject(long projectId) {
        return mybatisProjectRepository.findByProjectId(projectId);
    }

    @Transactional
    public void createProject(long userId, ProjectSaveRequestDto projectSaveRequestDto, MultipartFile multipartFile) {
        Optional<User> user = mybatisUserRepository.findByUserId(userId);
        Image image = awsS3ImageUploadService.upload(multipartFile);

        Project project = Project.builder()
            .leader(user.orElseThrow(NullPointerException::new))
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
    public void modifyProject(long userId, long projectId, ProjectUpdateRequestDto projectUpdateRequestDto, MultipartFile multipartFile) {
        Project project = mybatisProjectRepository.findByProjectId(projectId);

        validationProjectLeader(userId, project.getLeader().getUserId());

        if(multipartFile != null) {
            awsS3ImageUploadService.delete(project.getImage().getImageId());
            awsS3ImageUploadService.upload(multipartFile);
        }

        project.updateInfo(projectUpdateRequestDto);
        mybatisProjectRepository.update(project);
    }

    @Transactional
    public void projectApply(long userId, long projectPositionId, List<ProjectApplyRequestDto> projectApplyRequestDto) {
        boolean existProjectApply = mybatisProjectApplyRepository.existsProjectPositionIdAndUserId(projectPositionId, userId);

        if(existProjectApply) {
            throw new DuplicateApplyException("이미 신청한 프로젝트 입니다.");
        }

        Optional<User> user = mybatisUserRepository.findByUserId(userId);

        ProjectApply projectApply = ProjectApply.builder()
            .projectPosition(ProjectPosition.builder().projectPositionId(projectPositionId).build())
            .user(user.orElseThrow(NullPointerException::new))
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

    @Transactional
    public void addProjectPosition(long userId, long projectId, ProjectPositionSaveRequestDto projectPositionSaveRequestDto) {
        Project project = mybatisProjectRepository.findByProjectId(projectId);

        validationProjectLeader(userId, project.getLeader().getUserId());

        if(mybatisProjectPositionRepository.existsByProjectIdAndPositionId(projectId, projectPositionSaveRequestDto.getPositionId())) {
            throw new IllegalStateException("이미 등록된 포지션 입니다.");
        }

        mybatisProjectPositionRepository.save(ProjectPosition.builder()
            .project(project)
            .position(Position.builder().positionId(projectPositionSaveRequestDto.getPositionId()).build())
            .personnel(projectPositionSaveRequestDto.getPersonnel())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build());
    }

    @Transactional
    public void modifyProjectPosition(long userId, long projectPositionId, ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto) {
        ProjectPosition projectPosition = mybatisProjectPositionRepository.findById(projectPositionId);

        validationProjectLeader(userId, projectPosition.getProject().getLeader().getUserId());

        projectPosition.updateInfo(projectPositionUpdateRequestDto);
        mybatisProjectPositionRepository.update(projectPosition);
    }

    @Transactional
    public void deleteProjectPosition(long userId, long projectPositionId) {
        ProjectPosition projectPosition = mybatisProjectPositionRepository.findById(projectPositionId);

        validationProjectLeader(userId, projectPosition.getProject().getLeader().getUserId());

        if(mybatisProjectApplyRepository.existsByProjectPositionId(projectPositionId)) {
            throw new IllegalStateException("해당 포지션의 신청이 존재합니다.");
        }

        mybatisProjectPositionRepository.delete(projectPosition);
    }

    @Transactional
    public void addProjectSKill(long userId, long projectId, ProjectSkillSaveRequestDto projectSkillSaveRequestDto) {
        Project project = mybatisProjectRepository.findByProjectId(projectId);

        validationProjectLeader(userId, project.getLeader().getUserId());

        mybatisProjectSkillRepository.save(ProjectSkill.builder()
            .project(project)
            .skill(Skill.builder().skillId(projectSkillSaveRequestDto.getSkillId()).build())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build());
    }

    @Transactional
    public void deleteProjectSKill(long userId, long projectSkillId) {
        ProjectSkill projectSkill = mybatisProjectSkillRepository.findByProjectSkillId(projectSkillId);

        validationProjectLeader(userId, projectSkill.getProject().getLeader().getUserId());

        mybatisProjectSkillRepository.delete(projectSkill);

    }

    @Transactional
    public void addApplyQuestion(long userId, long projectId, ApplyQuestionSaveRequestDto applyQuestionSaveRequestDto) {
        Project project = mybatisProjectRepository.findByProjectId(projectId);

        validationProjectLeader(userId, project.getLeader().getUserId());

        mybatisApplyQuestionRepository.save(ApplyQuestion.builder()
            .project(project)
            .question(applyQuestionSaveRequestDto.getQuestion())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build());
    }

    @Transactional
    public void modifyApplyQuestion(long userId, long applyQuestionId, ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto) {
        ApplyQuestion applyQuestion = mybatisApplyQuestionRepository.findByApplyQuestionId(applyQuestionId);

        validationProjectLeader(userId, applyQuestion.getProject().getLeader().getUserId());

        applyQuestion.updateInfo(applyQuestionUpdateRequestDto);
        mybatisApplyQuestionRepository.update(applyQuestion);
    }

    @Transactional
    public void deleteApplyQuestion(long userId, long applyQuestionId) {
        ApplyQuestion applyQuestion = mybatisApplyQuestionRepository.findByApplyQuestionId(applyQuestionId);

        validationProjectLeader(userId, applyQuestion.getProject().getLeader().getUserId());

        mybatisApplyQuestionRepository.delete(applyQuestion);
    }

    private void validationProjectLeader(long loginUserId, long projectLeaderId) {
        if(loginUserId != projectLeaderId) {
            throw new NonCreatorException("프로젝트 리더가 아닙니다.");
        }
    }
}
