package org.example.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisLockRepository {

    private final RedisTemplate<String, String> restTemplate;

    public RedisLockRepository(RedisTemplate<String, String> restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean lock(Long key) {
        return restTemplate
                .opsForValue()
                .setIfAbsent(key.toString(), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unLock(Long key) {
        return restTemplate.delete(key.toString());
    }
}
