package com.runningmate.runningmate.comment.domain.repository;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import com.runningmate.runningmate.comment.domain.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisCommentRepository implements CommentRepository {

    private final CommentMapper commentMapper;

    @Override
    public Comment findByCommentId(long commentId) {
        return commentMapper.selectCommentByCommentId(commentId);
    }

    @Override
    public List<Comment> findByProjectId(long projectId, Pageable pageable) {
        return commentMapper.selectCommentByProjectId(projectId, pageable);
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentMapper.deleteComment(comment);
    }
}
