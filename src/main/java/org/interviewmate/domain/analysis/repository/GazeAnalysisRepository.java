package org.interviewmate.domain.analysis.repository;

import java.util.List;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GazeAnalysisRepository extends JpaRepository<GazeAnalysis, Long> {

    List<GazeAnalysis> findAllByInterview(Interview interview);
}
