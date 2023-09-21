package org.interviewmate.infra.redis.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 데이터 생성 (유효 시간 설정 포함)
     */
    public void saveWithExpirationDate(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    /**
     * 키 값으로 데이터 가져오기
     */
    public String findByKey(String key) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);

    }

}
