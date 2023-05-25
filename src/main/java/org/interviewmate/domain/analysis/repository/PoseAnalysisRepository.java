package org.interviewmate.domain.analysis.repository;

import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.interview.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseAnalysisRepository extends JpaRepository<PoseAnalysis, Long> {

    PoseAnalysis findAllByInterview(Interview interview);
}
