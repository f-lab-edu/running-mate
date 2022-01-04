package com.runningmate.runningmate.comment.controller;

import com.runningmate.runningmate.comment.dto.response.CommentInfoResponseDto;
import com.runningmate.runningmate.comment.dto.request.CommentSaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import com.runningmate.runningmate.comment.service.CommentService;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.aop.LoginCheck;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/project/{projectId}/comments")
    public ResponseEntity<List<CommentInfoResponseDto>> getComments(@PathVariable("projectId")long projectId,
                                                                    @RequestParam(value = "cursor", required = false, defaultValue = "0")int cursor,
                                                                    @RequestParam(value = "size", required = false, defaultValue = "10")int size) {
        List<CommentInfoResponseDto> response = commentService.getComments(projectId, cursor, size).stream()
            .map(CommentInfoResponseDto::of)
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @LoginCheck
    @PostMapping("/project/{projectId}/comment")
    public ResponseEntity<?> createComment(@PathVariable("projectId")long projectId, @RequestBody @Valid CommentSaveRequestDto commentSaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.createComment(userId, projectId, commentSaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LoginCheck
    @PostMapping("project/{projectId}/comment/{commentId}/comment-reply")
    public ResponseEntity<?> createCommentReply(@PathVariable("projectId")long projectId,
                                                @PathVariable("commentId")long commentId,
                                                @RequestBody @Valid CommentSaveRequestDto commentSaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.createComment(userId, projectId, commentId, commentSaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LoginCheck
    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<?> modifyComment(@PathVariable("commentId") long commentId, @RequestBody @Valid CommentUpdateRequestDto commentUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.modifyComment(userId, commentId, commentUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId")long commentId) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.deleteComment(userId, commentId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
