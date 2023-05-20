package org.interviewmate.domain.analysis.repository;

import org.interviewmate.domain.analysis.model.BehaviorAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorAnalysisRepository extends JpaRepository<BehaviorAnalysis, Long> {
}
