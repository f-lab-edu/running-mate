package com.runningmate.runningmate.project.controller;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.project.dto.ProjectSaveRequestDto;
import com.runningmate.runningmate.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestPart("project") @Valid ProjectSaveRequestDto projectCreateRequestDto, @RequestPart("file") MultipartFile multipartFile, HttpSession session) {
        String loginUserEmail = SessionUtils.getLoginSessionEmail(session);

        if (loginUserEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            projectService.createProject(loginUserEmail, projectCreateRequestDto, multipartFile);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
