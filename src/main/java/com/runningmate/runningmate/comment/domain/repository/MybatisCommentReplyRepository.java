package com.runningmate.runningmate.comment.domain.repository;

import com.runningmate.runningmate.comment.domain.entity.CommentReply;
import com.runningmate.runningmate.comment.domain.mapper.CommentReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisCommentReplyRepository implements CommentReplyRepository {

    private final CommentReplyMapper commentReplyMapper;

    @Override
    public CommentReply findByCommentReplyId(long commentReplyId) {
        return commentReplyMapper.selectCommentReplyByCommentReplyId(commentReplyId);
    }

    @Override
    public void save(CommentReply commentReply) {
        commentReplyMapper.insertCommentReply(commentReply);
    }

    @Override
    public void update(CommentReply commentReply) {
        commentReplyMapper.updateCommentReply(commentReply);
    }

    @Override
    public void delete(CommentReply commentReply) {
        commentReplyMapper.deleteCommentReply(commentReply);
    }
}
