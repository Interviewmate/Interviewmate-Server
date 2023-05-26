package org.interviewmate.domain.analysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.domain.interview.model.InterviewVideo;
import org.interviewmate.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GazeAnalysis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gaze_analysis_id")
    private Long gazeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @OneToOne
    @JoinColumn(name = "interview_video_id")
    private InterviewVideo interviewVideo;

    private String startTime;

    private String endTime;

    private String duringTime;

    @Builder
    public GazeAnalysis(InterviewVideo interviewVideo, Interview interview, String startTime, String endTime, String duringTime) {
        this.interviewVideo = interviewVideo;
        this.interview = interview;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duringTime = duringTime;
    }

}
