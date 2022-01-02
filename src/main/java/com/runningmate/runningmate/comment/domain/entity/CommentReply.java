package com.runningmate.runningmate.comment.domain.entity;

import com.runningmate.runningmate.comment.dto.request.CommentReplyUpdateRequestDto;
import com.runningmate.runningmate.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentReply {

    private long commentReplyId;
    private Comment comment;
    private User user;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void updateInfo(CommentReplyUpdateRequestDto commentReplyUpdateRequestDto) {
        this.contents = commentReplyUpdateRequestDto.getContents();
        this.updateDate = LocalDateTime.now();
    }
}
