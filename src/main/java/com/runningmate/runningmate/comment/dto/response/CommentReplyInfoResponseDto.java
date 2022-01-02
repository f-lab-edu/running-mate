package com.runningmate.runningmate.comment.dto.response;

import com.runningmate.runningmate.comment.domain.entity.CommentReply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyInfoResponseDto {
    private long commentReplyId;
    private String contents;
    private long userId;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static CommentReplyInfoResponseDto of(CommentReply commentReply) {
        return CommentReplyInfoResponseDto.builder()
            .commentReplyId(commentReply.getCommentReplyId())
            .contents(commentReply.getContents())
            .userId(commentReply.getUser().getUserId())
            .email(commentReply.getUser().getEmail())
            .nickname(commentReply.getUser().getNickName())
            .createDate(commentReply.getCreateDate())
            .updateDate(commentReply.getUpdateDate())
            .build();
    }
}
