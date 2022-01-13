package com.runningmate.runningmate.like.service;

import com.runningmate.runningmate.common.RedisCacheService;
import com.runningmate.runningmate.common.RedisCacheService.KeyPrefix;
import com.runningmate.runningmate.like.domain.repository.LikeCacheRepository;
import com.runningmate.runningmate.project.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ProjectRepository mybatisProjectRepository;
    private final LikeCacheRepository likeCacheRepository;
    private final RedisCacheService redisCacheService;

    @Transactional
    public void addLike(long userId, long projectId) {
        validationProjectExists(projectId);

        likeCacheRepository.addLike(redisCacheService.generateKey(KeyPrefix.LIKE, projectId), userId);
        likeCacheRepository.increaseLike(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId));
    }

    @Transactional
    public void deleteLike(long userId, long projectId) {
        validationProjectExists(projectId);

        likeCacheRepository.deleteLike(redisCacheService.generateKey(KeyPrefix.LIKE, projectId), userId);
        likeCacheRepository.decreaseLike(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId));
    }

    private void validationProjectExists(long projectId) {
        if(!mybatisProjectRepository.existsByProjectId(projectId)) {
            throw new NullPointerException("존재하지 않는 프로젝트 입니다.");
        }
    }
}
