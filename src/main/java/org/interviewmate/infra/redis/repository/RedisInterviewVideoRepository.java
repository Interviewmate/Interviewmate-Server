package org.interviewmate.infra.redis.repository;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisInterviewVideoRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private ListOperations<String, String> listOperations;

    @PostConstruct
    public void init() {
        listOperations = redisTemplate.opsForList();
    }

    /**
     * 리스트 생성
     */
    public void save(String key, List<String> value) {
        listOperations.rightPushAll(key, value);
    }

    /**
     * 키 값으로 데이터 가져오기
     */
    public String findByKey(String key) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);

    }

    /**
     * 키 값으로 데이터 유무 확인
     */
    public boolean isExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    /**
     * 데이터 삭제
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
