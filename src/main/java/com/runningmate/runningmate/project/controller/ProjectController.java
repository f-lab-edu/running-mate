package com.runningmate.runningmate.project.controller;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.common.utils.ValidList;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ApplyQuestionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectPositionUpdateRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSkillSaveRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectUpdateRequestDto;
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
        List<ProjectInfoResponseDto> response = projectService.getProjects(projectSearchRequestDto).stream()
            .map(ProjectInfoResponseDto::of)
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectInfoResponseDto> getProject(@PathVariable("projectId") long projectId) {
        ProjectInfoResponseDto response = ProjectInfoResponseDto.of(projectService.getProject(projectId));

        return ResponseEntity.ok().body(response);
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
    @PatchMapping("/project-position/{projectPositionId}")
    public ResponseEntity<?> modifyProjectPosition(@PathVariable("projectPositionId") long projectPositionId, @RequestBody @Valid ProjectPositionUpdateRequestDto projectPositionUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyProjectPosition(userId, projectPositionId, projectPositionUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/project-position/{projectPositionId}")
    public ResponseEntity<?> deleteProjectPosition(@PathVariable("projectPositionId") long projectPositionId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteProjectPosition(userId, projectPositionId);

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
    @DeleteMapping("/project-skill/{projectSkillId}")
    public ResponseEntity<?> deleteProjectSkill(@PathVariable("projectSkillId") long projectSkillId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteProjectSKill(userId, projectSkillId);

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
    @PatchMapping("/apply-question/{applyQuestionId}")
    public ResponseEntity<?> modifyApplyQuestion(@PathVariable("applyQuestionId") long applyQuestionId, @RequestBody @Valid ApplyQuestionUpdateRequestDto applyQuestionUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.modifyApplyQuestion(userId, applyQuestionId, applyQuestionUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/apply-question/{applyQuestionId}")
    public ResponseEntity<?> deleteApplyQuestion(@PathVariable("applyQuestionId") long applyQuestionId) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.deleteApplyQuestion(userId, applyQuestionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
