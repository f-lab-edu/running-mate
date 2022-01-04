package com.runningmate.runningmate.comment.domain.entity;

import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {

    private long commentId;
    private Project project;
    private User user;
    private String contents;
    private long parentId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<Comment> replies = new ArrayList<>();

    public void updateInfo(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.contents = commentUpdateRequestDto.getContents();
        this.updateDate = LocalDateTime.now();
    }
}
