package com.runningmate.runningmate.like.controller;

import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/project/{projectId}/like")
    public ResponseEntity<?> addLike(@PathVariable("projectId") long projectId) {
        long userId = SessionUtils.getLoginSessionUserId();

        likeService.addLike(userId, projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/project/{projectId}/like")
    public ResponseEntity<?> deleteLike(@PathVariable("projectId")long projectId) {
        long userId = SessionUtils.getLoginSessionUserId();

        likeService.deleteLike(userId, projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
