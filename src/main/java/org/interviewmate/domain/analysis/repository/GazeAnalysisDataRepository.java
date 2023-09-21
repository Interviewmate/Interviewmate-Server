package org.interviewmate.domain.analysis.repository;

import java.util.List;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysisData;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GazeAnalysisDataRepository extends JpaRepository<GazeAnalysisData, Long> {

    List<GazeAnalysisData> findAllByGazeAnalysis(GazeAnalysis gazeAnalysis);

    List<GazeAnalysisData> findAllByInterviewVideo(InterviewVideo interviewVideo);
}
