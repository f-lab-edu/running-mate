package com.runningmate.runningmate.project.controller;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.common.utils.ValidList;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectUpdateRequestDto;
import com.runningmate.runningmate.project.dto.response.ApplyAnswerInfoResponseDto;
import com.runningmate.runningmate.project.dto.response.ProjectApplyInfoResponseDto;
import com.runningmate.runningmate.project.dto.response.ProjectInfoResponseDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.service.ProjectService;
import com.runningmate.runningmate.user.aop.LoginCheck;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectInfoResponseDto>> getProjects(@RequestBody ProjectSearchRequestDto projectSearchRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        return ResponseEntity.ok().body(projectService.getProjects(userId, projectSearchRequestDto));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectInfoResponseDto> getProject(@PathVariable("projectId") long projectId) {
        long userId = SessionUtils.getLoginSessionUserId();

        return ResponseEntity.ok().body(projectService.getProject(userId, projectId));
    }

    @LoginCheck
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestPart("project") @Valid ProjectSaveRequestDto projectCreateRequestDto, @RequestPart("file") MultipartFile multipartFile) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.createProject(userId, projectCreateRequestDto, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LoginCheck
    @PatchMapping("/project/{projectId}")
    public ResponseEntity<?> modifyProject(
        @PathVariable("projectId") long projectId,
        @RequestPart(value = "project") @Valid ProjectUpdateRequestDto projectUpdateRequestDto,
        @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyProject(userId, projectId, projectUpdateRequestDto, multipartFile);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") long projectId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteProject(userId, projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PostMapping("/project-apply/{projectPositionId}")
    public ResponseEntity<?> projectApply(@PathVariable("projectPositionId") long projectPositionId, @RequestBody @Valid ValidList<ProjectApplyRequestDto> projectApplyRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.projectApply(userId, projectPositionId, projectApplyRequestDto.getList());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PostMapping("/project/{projectId}/project-position")
    public ResponseEntity<?> addProjectPosition(@PathVariable("projectId") long projectId, @RequestBody @Valid ProjectPositionSaveRequestDto projectPositionSaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.addProjectPosition(userId, projectId, projectPositionSaveRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PatchMapping("/project/{projectId}/project-position/{projectPositionId}")
    public ResponseEntity<?> modifyProjectPosition(@PathVariable("projectId")long projectId,
                                                    @PathVariable("projectPositionId") long projectPositionId,
                                                    @RequestBody @Valid ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyProjectPosition(userId, projectId, projectPositionId, projectPositionUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/project/{projectId}/project-position/{projectPositionId}")
    public ResponseEntity<?> deleteProjectPosition(@PathVariable("projectId") long projectId,
                                                    @PathVariable("projectPositionId") long projectPositionId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteProjectPosition(userId, projectId, projectPositionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PostMapping("/project/{projectId}/project-skill")
    public ResponseEntity<?> addProjectSkill(@PathVariable("projectId") long projectId, @RequestBody @Valid ProjectSkillSaveRequestDto projectSkillSaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.addProjectSKill(userId, projectId, projectSkillSaveRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/project/{projectId}/project-skill/{projectSkillId}")
    public ResponseEntity<?> deleteProjectSkill(@PathVariable("projectId") long projectId,
                                                @PathVariable("projectSkillId") long projectSkillId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteProjectSKill(userId, projectId, projectSkillId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PostMapping("/project/{projectId}/apply-question")
    public ResponseEntity<?> addApplyQuestion(@PathVariable("projectId") long projectId, @RequestBody @Valid ApplyQuestionSaveRequestDto applyQuestionSaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.addApplyQuestion(userId, projectId, applyQuestionSaveRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @PatchMapping("/project/{projectId}/apply-question/{applyQuestionId}")
    public ResponseEntity<?> modifyApplyQuestion(@PathVariable("projectId") long projectId,
                                                    @PathVariable("applyQuestionId") long applyQuestionId,
                                                    @RequestBody @Valid ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyApplyQuestion(userId, projectId, applyQuestionId, applyQuestionUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/project/{projectId}/apply-question/{applyQuestionId}")
    public ResponseEntity<?> deleteApplyQuestion(@PathVariable("projectId") long projectId,
                                                @PathVariable("applyQuestionId") long applyQuestionId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteApplyQuestion(userId, projectId, applyQuestionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @GetMapping("/project/{projectId}/project-applies")
    public ResponseEntity<List<ProjectApplyInfoResponseDto>> getProjectApplies(@PathVariable("projectId") long projectId) {
        long userId = SessionUtils.getLoginSessionUserId();

        List<ProjectApplyInfoResponseDto> response = projectService.getProjectApplies(userId, projectId).stream()
            .map(ProjectApplyInfoResponseDto::of)
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @LoginCheck
    @GetMapping("/project-apply/{projectApplyId}/apply-answer")
    public ResponseEntity<List<ApplyAnswerInfoResponseDto>> getApplyAnswers(@PathVariable("projectApplyId")long projectApplyId) {
        long userId = SessionUtils.getLoginSessionUserId();

        List<ApplyAnswerInfoResponseDto> response = projectService.getApplyAnswers(userId, projectApplyId).stream()
            .map(ApplyAnswerInfoResponseDto::of)
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @LoginCheck
    @PatchMapping("/project-apply/{projectApplyId}")
    public ResponseEntity<?> modifyProjectApply(@PathVariable("projectApplyId")long projectApplyId,
                                                @RequestBody @Valid ProjectApplyUpdateRequestDto projectApplyUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyProjectApply(userId, projectApplyId, projectApplyUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
