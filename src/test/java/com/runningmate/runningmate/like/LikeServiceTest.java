package com.runningmate.runningmate.like;

import static org.mockito.Mockito.*;

import com.runningmate.runningmate.common.RedisCacheService;
import com.runningmate.runningmate.common.RedisCacheService.KeyPrefix;
import com.runningmate.runningmate.like.domain.repository.LikeCacheRepository;
import com.runningmate.runningmate.like.service.LikeService;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeCacheRepository likeCacheRepository;

    @Mock
    private RedisCacheService redisCacheService;

    @Mock
    private ProjectRepository mybatisProjectRepository;

    @Test
    @DisplayName("좋아요 추가 성공")
    void successLikeAdd() {
        String likeKey = "like:1";
        String likeCountKey = "like_count:1";
        long userId = 1L;
        long projectId = 1L;

        when(mybatisProjectRepository.existsByProjectId(projectId)).thenReturn(true);
        when(redisCacheService.generateKey(KeyPrefix.LIKE, projectId)).thenReturn(likeKey);
        when(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId)).thenReturn(likeCountKey);

        likeService.addLike(userId, projectId);

        verify(likeCacheRepository, times(1)).addLike(likeKey, userId);
        verify(likeCacheRepository, times(1)).increaseLike(likeCountKey);
    }

    @Test
    @DisplayName("좋아요 추가 실패 - 존재하지 않는 프로젝트")
    void failLikeAdd() {
        long userId = 1L;
        long projectId = 1L;

        when(mybatisProjectRepository.existsByProjectId(projectId)).thenReturn(false);

        Assertions.assertThrows(NullPointerException.class, () -> {
            likeService.addLike(userId, projectId);
        });
    }

    @Test
    @DisplayName("좋아요 삭제 성공")
    void successLikeDelete() {
        String likeKey = "like:1";
        String likeCountKey = "like_count:1";
        long userId = 1L;
        long projectId = 1L;

        when(mybatisProjectRepository.existsByProjectId(projectId)).thenReturn(true);
        when(redisCacheService.generateKey(KeyPrefix.LIKE, projectId)).thenReturn(likeKey);
        when(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId)).thenReturn(likeCountKey);

        likeService.deleteLike(userId, projectId);

        verify(likeCacheRepository, times(1)).deleteLike(likeKey, userId);
        verify(likeCacheRepository, times(1)).decreaseLike(likeCountKey);
    }

    @Test
    @DisplayName("좋아요 삭제 실패 - 존재하지 않는 프로젝트")
    void failLikeDelete() {
        long userId = 1L;
        long projectId = 1L;

        when(mybatisProjectRepository.existsByProjectId(projectId)).thenReturn(false);

        Assertions.assertThrows(NullPointerException.class, () -> {
            likeService.deleteLike(userId, projectId);
        });
    }
}
