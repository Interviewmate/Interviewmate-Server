package org.interviewmate.infra.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate template;

    /**
     * 키 값으로 데이터 가져오기
     */
    public String getData(String key) {

        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);

    }

    /**
     * 키 값으로 데이터 유무 확인
     */
    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    /**
     * 데이터 유효 시간 설정
     */
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    /**
     * 데이터 삭제
     */
    public void deleteData(String key) {
        template.delete(key);
    }

}
