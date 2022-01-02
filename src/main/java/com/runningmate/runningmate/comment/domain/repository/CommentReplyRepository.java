package com.runningmate.runningmate.comment.domain.repository;

import com.runningmate.runningmate.comment.domain.entity.CommentReply;

public interface CommentReplyRepository {

    public CommentReply findByCommentReplyId(long commentReplyId);

    public void save(CommentReply commentReply);

    public void update(CommentReply commentReply);

    public void delete(CommentReply commentReply);
}
