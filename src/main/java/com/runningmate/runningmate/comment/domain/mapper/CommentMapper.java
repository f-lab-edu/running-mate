package com.runningmate.runningmate.comment.domain.mapper;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface CommentMapper {

    public Comment selectCommentByCommentId(long commentId);

    public List<Comment> selectCommentByProjectId(@Param("projectId") long projectId, @Param("pageable") Pageable pageable);

    public void insertComment(Comment comment);

    public void updateComment(Comment comment);

    public void deleteComment(Comment comment);
}
