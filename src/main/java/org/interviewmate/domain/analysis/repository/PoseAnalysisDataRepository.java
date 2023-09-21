package org.interviewmate.domain.analysis.repository;

import java.util.List;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.PoseAnalysisData;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseAnalysisDataRepository extends JpaRepository<PoseAnalysisData, Long> {

    List<PoseAnalysisData> findAllByPoseAnalysis(PoseAnalysis poseAnalysis);
    List<PoseAnalysisData> findAllByInterviewVideo(InterviewVideo interviewVideo);

}
