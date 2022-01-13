package com.runningmate.runningmate.common;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    @Getter
    public enum KeyPrefix {
        LIKE("like"),
        LIKE_COUNT("like_count");

        private final String key;

        KeyPrefix(String key) {
            this.key = key;
        }
    }

    public String generateKey(KeyPrefix keyPrefix, long id) {
        return String.join(":", keyPrefix.getKey(), String.valueOf(id));
    }

    public List<String> generateKeys(KeyPrefix keyPrefix, List<Long> keys) {
        return keys.stream()
            .map(key -> generateKey(keyPrefix, key))
            .collect(Collectors.toList());
    }
}
