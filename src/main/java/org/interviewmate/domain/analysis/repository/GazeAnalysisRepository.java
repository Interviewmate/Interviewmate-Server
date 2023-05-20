package org.interviewmate.domain.analysis.repository;

import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GazeAnalysisRepository extends JpaRepository<GazeAnalysis, Long> {
}
