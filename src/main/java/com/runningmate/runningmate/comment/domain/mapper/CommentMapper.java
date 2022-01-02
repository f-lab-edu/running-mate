package com.runningmate.runningmate.comment.domain.mapper;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    public Comment selectCommentByCommentId(long commentId);

    public List<Comment> selectCommentByProjectId(long projectId);

    public void insertComment(Comment comment);

    public void updateComment(Comment comment);

    public void deleteComment(Comment comment);
}
