package com.runningmate.runningmate.comment.controller;

import com.runningmate.runningmate.comment.dto.request.CommentReplySaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentReplyUpdateRequestDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/project/{projectId}/comments")
    public ResponseEntity<List<CommentInfoResponseDto>> getComments(@PathVariable("projectId")long projectId) {

        List<CommentInfoResponseDto> response = commentService.getComments(projectId).stream()
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

    @LoginCheck
    @PostMapping("/comment/{commentId}/comment-reply")
    public ResponseEntity<?> addCommentReply(@PathVariable("commentId")long commentId, @RequestBody @Valid CommentReplySaveRequestDto commentReplySaveRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.addCommentReply(userId, commentId, commentReplySaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LoginCheck
    @PatchMapping("/comment-reply/{commentReplyId}")
    public ResponseEntity<?> modifyCommentReply(@PathVariable("commentReplyId")long commentReplyId, @RequestBody @Valid CommentReplyUpdateRequestDto commentReplyUpdateRequestDto) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.modifyCommentReply(userId, commentReplyId, commentReplyUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @LoginCheck
    @DeleteMapping("/comment-reply/{commentReplyId}")
    public ResponseEntity<?> deleteCommentReply(@PathVariable("commentReplyId")long commentReplyId) {
        long userId = SessionUtils.getLoginSessionUserId();

        commentService.deleteCommentReply(userId, commentReplyId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
