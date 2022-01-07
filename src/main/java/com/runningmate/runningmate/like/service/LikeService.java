package com.runningmate.runningmate.like.service;

import com.runningmate.runningmate.common.RedisCacheService;
import com.runningmate.runningmate.common.RedisCacheService.KeyPrefix;
import com.runningmate.runningmate.like.domain.repository.LikeCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeCacheRepository likeCacheRepository;
    private final RedisCacheService redisCacheService;

    @Transactional
    public void addLike(long userId, long projectId) {
        likeCacheRepository.addLike(redisCacheService.generateKey(KeyPrefix.LIKE, projectId), userId);
        likeCacheRepository.increaseLike(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId));
    }

    @Transactional
    public void deleteLike(long userId, long projectId) {
        likeCacheRepository.deleteLike(redisCacheService.generateKey(KeyPrefix.LIKE, projectId), userId);
        likeCacheRepository.decreaseLike(redisCacheService.generateKey(KeyPrefix.LIKE_COUNT, projectId));
    }
}
