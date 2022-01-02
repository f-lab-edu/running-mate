package com.runningmate.runningmate.comment.domain.mapper;

import com.runningmate.runningmate.comment.domain.entity.CommentReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentReplyMapper {

    public CommentReply selectCommentReplyByCommentReplyId(long commentReplyId);

    public void insertCommentReply(CommentReply commentReply);

    public void updateCommentReply(CommentReply commentReply);

    public void deleteCommentReply(CommentReply commentReply);
}
