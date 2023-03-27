package org.interviewmate.debug.baseentity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final JpaTestEntityRepository testEntityRepository;

    public void create(String name){
        testEntityRepository.save(TestEntity.builder().name(name).build());
    }

    public List<TestEntity> findAll(){
        return testEntityRepository.findAll();
    }

    public long count(){
        return testEntityRepository.count();
    }
}
