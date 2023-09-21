package org.interviewmate.domain.analysis.repository;

import java.util.List;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysisData;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GazeAnalysisRepository extends JpaRepository<GazeAnalysis, Long> {

    GazeAnalysis findAllByInterview(Interview interview);

}
