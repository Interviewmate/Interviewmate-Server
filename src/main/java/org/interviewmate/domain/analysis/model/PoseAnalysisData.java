package org.interviewmate.domain.analysis.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.InterviewVideo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PoseAnalysisData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pose_analysis_data_id")
    private Long poseAnalysisDataId;

    @ManyToOne
    @JoinColumn(name = "pose_analysis_id")
    PoseAnalysis poseAnalysis;

    @OneToOne
    @JoinColumn(name = "interview_video_id")
    private InterviewVideo interviewVideo;

    private Double startTime;

    private Double endTime;

    private Double duringTime;

    @Builder
    public PoseAnalysisData(PoseAnalysis poseAnalysis, InterviewVideo interviewVideo, Double startTime, Double endTime, Double duringTime) {
        this.poseAnalysis = poseAnalysis;
        this.interviewVideo = interviewVideo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duringTime = duringTime;
    }

}
