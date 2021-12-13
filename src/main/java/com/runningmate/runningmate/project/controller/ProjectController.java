package com.runningmate.runningmate.project.controller;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.common.utils.ValidList;
import com.runningmate.runningmate.project.dto.request.ProjectApplyRequestDto;
import com.runningmate.runningmate.project.dto.request.ProjectSearchRequestDto;
import com.runningmate.runningmate.project.dto.response.ProjectInfoResponseDto;
import com.runningmate.runningmate.project.dto.request.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.service.ProjectService;
import com.runningmate.runningmate.user.aop.LoginCheck;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project")
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

    @LoginCheck(userLevel = UserLevel.CUSTOMER)
    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestPart("project") @Valid ProjectSaveRequestDto projectCreateRequestDto, @RequestPart("file") MultipartFile multipartFile) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.createProject(userId, projectCreateRequestDto, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LoginCheck(userLevel = UserLevel.CUSTOMER)
    @PostMapping("/project-apply/{projectPositionId}")
    public ResponseEntity<?> modifyProject(@PathVariable("projectPositionId") long projectPositionId, @RequestBody @Valid ValidList<ProjectApplyRequestDto> projectApplyRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        projectService.projectApply(userId, projectPositionId, projectApplyRequestDto.getList());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
