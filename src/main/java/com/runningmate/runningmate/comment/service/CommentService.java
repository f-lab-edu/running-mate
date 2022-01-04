package com.runningmate.runningmate.comment.service;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import com.runningmate.runningmate.comment.domain.repository.CommentRepository;
import com.runningmate.runningmate.comment.dto.request.CommentSaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import com.runningmate.runningmate.project.domain.entity.Project;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository mybatisCommentRepository;
    private final UserRepository mybatisUserRepository;

    @Transactional(readOnly = true)
    public List<Comment> getComments(long projectId, int cursor, int size) {
        Map<Long, Comment> commentMap = new HashMap<>();

        mybatisCommentRepository.findByProjectId(projectId, PageRequest.of(cursor, size))
            .forEach(comment -> {
                if(comment.getParentId() == 0) commentMap.put(comment.getCommentId(), comment);
                else commentMap.get(comment.getParentId()).getReplies().add(comment);
            });

        return new ArrayList<>(commentMap.values());
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
    public void createComment(long userId, long projectId, long commentId, CommentSaveRequestDto commentSaveRequestDto) {
        Optional<User> user = mybatisUserRepository.findByUserId(userId);

        mybatisCommentRepository.save(Comment.builder()
            .project(Project.builder().projectId(projectId).build())
            .user(user.orElseThrow(NullPointerException::new))
            .contents(commentSaveRequestDto.getContents())
            .parentId(commentId)
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

    private void validationWriter(long loginUserId, long writerUserId) {
        if(loginUserId != writerUserId) {
            throw new IllegalStateException("작성자가 아닙니다.");
        }
    }
}
