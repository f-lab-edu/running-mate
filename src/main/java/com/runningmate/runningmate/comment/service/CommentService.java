package com.runningmate.runningmate.comment.service;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import com.runningmate.runningmate.comment.domain.entity.CommentReply;
import com.runningmate.runningmate.comment.domain.repository.CommentReplyRepository;
import com.runningmate.runningmate.comment.domain.repository.CommentRepository;
import com.runningmate.runningmate.comment.dto.request.CommentReplySaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentReplyUpdateRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentSaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository mybatisCommentRepository;
    private final CommentReplyRepository mybatisCommentReplyRepository;
    private final UserRepository mybatisUserRepository;

    @Transactional(readOnly = true)
    public List<Comment> getComments(long projectId) {
        return mybatisCommentRepository.findByProjectId(projectId);
    }

    @Transactional
    public void createComment(long userId, long projectId, CommentSaveRequestDto commentSaveRequestDto) {
        Optional<User> user = mybatisUserRepository.findByUserId(userId);

        mybatisCommentRepository.save(Comment.builder()
            .project(Project.builder().projectId(projectId).build())
            .user(user.orElseThrow(NullPointerException::new))
            .contents(commentSaveRequestDto.getContents())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build());
    }

    @Transactional
    public void modifyComment(long userId, long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = mybatisCommentRepository.findByCommentId(commentId);

        validationWriter(userId, comment.getUser().getUserId());

        comment.updateInfo(commentUpdateRequestDto);
        mybatisCommentRepository.update(comment);
    }

    @Transactional
    public void deleteComment(long userId, long commentId) {
        Comment comment = mybatisCommentRepository.findByCommentId(commentId);

        validationWriter(userId, comment.getUser().getUserId());

        mybatisCommentRepository.delete(comment);
    }

    public void addCommentReply(long userId, long commentId, CommentReplySaveRequestDto commentReplySaveRequestDto) {
        Comment comment = mybatisCommentRepository.findByCommentId(commentId);
        Optional<User> user = mybatisUserRepository.findByUserId(userId);

        mybatisCommentReplyRepository.save(CommentReply.builder()
            .comment(comment)
            .user(user.orElseThrow(NullPointerException::new))
            .contents(commentReplySaveRequestDto.getContents())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build());
    }

    public void modifyCommentReply(long userId, long commentReplyId, CommentReplyUpdateRequestDto commentReplyUpdateRequestDto) {
        CommentReply commentReply = mybatisCommentReplyRepository.findByCommentReplyId(commentReplyId);

        validationWriter(userId, commentReply.getUser().getUserId());

        commentReply.updateInfo(commentReplyUpdateRequestDto);
        mybatisCommentReplyRepository.update(commentReply);
    }

    public void deleteCommentReply(long userId, long commentReplyId) {
        CommentReply commentReply = mybatisCommentReplyRepository.findByCommentReplyId(commentReplyId);

        validationWriter(userId, commentReply.getUser().getUserId());

        mybatisCommentReplyRepository.delete(commentReply);
    }

    private void validationWriter(long loginUserId, long writerUserId) {
        if(loginUserId != writerUserId) {
            throw new IllegalStateException("작성자가 아닙니다.");
        }
    }
}
