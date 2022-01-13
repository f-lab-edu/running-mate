package com.runningmate.runningmate.comment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.runningmate.runningmate.comment.domain.entity.Comment;
import com.runningmate.runningmate.comment.domain.repository.CommentRepository;
import com.runningmate.runningmate.comment.dto.request.CommentSaveRequestDto;
import com.runningmate.runningmate.comment.dto.request.CommentUpdateRequestDto;
import com.runningmate.runningmate.comment.service.CommentService;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository mybatisCommentRepository;

    @Mock
    private UserRepository mybatisUserRepository;

    private User user;

    private Comment comment;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .userId(1L)
            .email("test@gmail.com")
            .nickName("nickname")
            .password("password")
            .build();

        comment = Comment.builder()
            .commentId(1L)
            .user(user)
            .contents("Contents")
            .build();
    }

    @Test
    @DisplayName("댓글 조회 성공")
    void successCommentGet() {
        commentService.getComments(1L, 0, 10);

        verify(mybatisCommentRepository, times(1)).findByProjectId(1L, PageRequest.of(0, 10));
    }

    @Test
    @DisplayName("댓글 등록 성공")
    void successCommentAdd() {
        when(mybatisUserRepository.findByUserId(1L)).thenReturn(Optional.of(user));

        commentService.createComment(1L, 1L, new CommentSaveRequestDto());

        verify(mybatisCommentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 등록 실패 - 존재하지 않는 유저")
    void failCommentAdd() {
        when(mybatisUserRepository.findByUserId(1L)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            commentService.createComment(1L, 1L, new CommentSaveRequestDto());
        });
    }

    @Test
    @DisplayName("대댓글 등록 성공")
    void successCommentReplyAdd() {
        when(mybatisUserRepository.findByUserId(1L)).thenReturn(Optional.of(user));

        commentService.createComment(1L, 1L, 1L, new CommentSaveRequestDto());

        verify(mybatisCommentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void successCommentModify() {
        when(mybatisCommentRepository.findByCommentId(1L)).thenReturn(comment);

        commentService.modifyComment(1L, 1L, new CommentUpdateRequestDto());

        verify(mybatisCommentRepository, times(1)).update(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void successCommentDelete() {
        when(mybatisCommentRepository.findByCommentId(1L)).thenReturn(comment);

        commentService.deleteComment(1L, 1L);

        verify(mybatisCommentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    @DisplayName("작성자 검증 성공 테스트")
    void successWriterValidation() {
        when(mybatisCommentRepository.findByCommentId(1L)).thenReturn(Comment.builder()
            .commentId(1L)
            .user(User.builder()
                .userId(1L)
                .email("test@gmail.com")
                .nickName("nickname")
                .password("password")
                .build())
            .contents("Contents")
            .build());

        commentService.deleteComment(1L, 1L);

        verify(mybatisCommentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    @DisplayName("작성자 검증 실패 테스트")
    void failWriterValidation() {
        when(mybatisCommentRepository.findByCommentId(1L)).thenReturn(Comment.builder()
            .commentId(1L)
            .user(User.builder()
                .userId(2L)
                .email("test@gmail.com")
                .nickName("nickname")
                .password("password")
                .build())
            .contents("Contents")
            .build());

        assertThrows(IllegalStateException.class, () -> {
            commentService.deleteComment(1L, 1L);
        });
    }
}
