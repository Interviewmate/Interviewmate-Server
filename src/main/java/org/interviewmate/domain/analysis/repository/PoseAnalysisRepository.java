package org.interviewmate.domain.analysis.repository;

import java.util.List;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseAnalysisRepository extends JpaRepository<PoseAnalysis, Long> {

    List<PoseAnalysis> findAllByInterview(Interview interview);
    List<PoseAnalysis> findAllByInterviewVideo(InterviewVideo interviewVideo);

}
