package org.interviewmate.domain.behavior.service;

import lombok.RequiredArgsConstructor;
import org.interviewmate.domain.behavior.model.BehaviorAnalysis;
import org.interviewmate.domain.behavior.repository.BehaviorAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BehaviorAnalysisService {

    private final BehaviorAnalysisRepository behaviorAnalysisRepository;

    public BehaviorAnalysis createBehaviorAnalysis(){
        return behaviorAnalysisRepository.save(new BehaviorAnalysis());
    }
}
