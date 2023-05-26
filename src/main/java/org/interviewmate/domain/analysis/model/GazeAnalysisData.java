package org.interviewmate.domain.analysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.InterviewVideo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GazeAnalysisData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gaze_analysis_data_id")
    private Long gazeAnalysisDataId;

    @ManyToOne
    @JoinColumn(name = "gaze_analysis_id")
    private GazeAnalysis gazeAnalysis;

    @OneToOne
    @JoinColumn(name = "interview_video_id")
    private InterviewVideo interviewVideo;

    private Double startTime;

    private Double endTime;

    private Double duringTime;

    @Builder
    public GazeAnalysisData(GazeAnalysis gazeAnalysis, InterviewVideo interviewVideo, Double startTime, Double endTime, Double duringTime) {
        this.gazeAnalysis = gazeAnalysis;
        this.interviewVideo = interviewVideo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duringTime = duringTime;
    }

}
