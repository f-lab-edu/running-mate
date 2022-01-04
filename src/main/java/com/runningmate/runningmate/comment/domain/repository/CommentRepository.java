package com.runningmate.runningmate.comment.domain.repository;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {

    public Comment findByCommentId(long commentId);

    public List<Comment> findByProjectId(long projectId, Pageable pageable);

    public void save(Comment comment);

    public void update(Comment comment);

    public void delete(Comment comment);
}
