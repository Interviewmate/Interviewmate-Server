package org.interviewmate.domain.behavior.repository;

import org.interviewmate.domain.behavior.model.BehaviorAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorAnalysisRepository extends JpaRepository<BehaviorAnalysis, Long> {
}
