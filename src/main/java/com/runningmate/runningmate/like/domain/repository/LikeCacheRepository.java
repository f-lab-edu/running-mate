package com.runningmate.runningmate.like.domain.repository;

import com.runningmate.runningmate.common.RedisCacheService;
import com.runningmate.runningmate.common.RedisCacheService.KeyPrefix;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeCacheRepository {
    private final int DELTA_VALUE = 1;

    private final RedisCacheService redisCacheService;
    private final RedisTemplate<String, Object> redisCacheTemplate;
    private final StringRedisTemplate stringCacheRedisTemplate;

    public Map<Long, Boolean> existLike(long userId, List<Long> ids) {
        RedisSerializer keySerializer = redisCacheTemplate.getKeySerializer();
        RedisSerializer valueSerializer = redisCacheTemplate.getValueSerializer();

        String strUserId = String.valueOf(userId);

        List<String> strKeys = redisCacheService.generateKeys(KeyPrefix.LIKE, ids);
        List<Object> exists = redisCacheTemplate.executePipelined(
            (RedisCallback<Object>) connection -> {

                strKeys.forEach(key -> {
                    connection.sIsMember(keySerializer.serialize(key), valueSerializer.serialize(strUserId));
                });

                return null;
            });

        Map<Long, Boolean> existMap = new HashMap<>();

        for(int i = 0; i < exists.size(); i++) {
            existMap.put(ids.get(i), (Boolean) exists.get(i));
        }

        return existMap;
    }

    public Map<Long, Integer> findLikeCount(List<Long> ids) {
        List<String> strKeys = redisCacheService.generateKeys(KeyPrefix.LIKE_COUNT, ids);
        List<String> counts = stringCacheRedisTemplate.opsForValue().multiGet(strKeys);

        Map<Long, Integer> countMap = new HashMap<>();

        for(int i = 0; i < counts.size(); i++) {
            String count = counts.get(i);

            countMap.put(ids.get(i), count == null ? 0 : Integer.parseInt(count));
        }

        return countMap;
    }

    public void addLike(String key, long userId) {
        redisCacheTemplate.opsForSet().add(key, String.valueOf(userId));
    }

    public void deleteLike(String key, long userId) {
        redisCacheTemplate.opsForSet().remove(key, String.valueOf(userId));
    }

    public void increaseLike(String key) {
        redisCacheTemplate.opsForValue().increment(key, DELTA_VALUE);
    }

    public void decreaseLike(String key) {
        redisCacheTemplate.opsForValue().decrement(key, DELTA_VALUE);
    }
}
