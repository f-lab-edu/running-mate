package com.runningmate.runningmate.comment.dto.response;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoResponseDto {

    private long commentId;
    private long projectId;
    private long userId;
    private String email;
    private String nickname;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<CommentInfoResponseDto> replies;

    public static CommentInfoResponseDto of(Comment comment) {
        return CommentInfoResponseDto.builder()
            .commentId(comment.getCommentId())
            .projectId(comment.getProject().getProjectId())
            .userId(comment.getUser().getUserId())
            .email(comment.getUser().getEmail())
            .nickname(comment.getUser().getNickName())
            .contents(comment.getContents())
            .createDate(comment.getCreateDate())
            .updateDate(comment.getUpdateDate())
            .replies(comment.getReplies().stream()
                .map(CommentInfoResponseDto::of)
                .collect(Collectors.toList()))
            .build();
    }
}
