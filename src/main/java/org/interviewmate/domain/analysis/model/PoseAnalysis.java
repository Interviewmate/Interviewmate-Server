package org.interviewmate.domain.analysis.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.global.common.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PoseAnalysis extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pose_analysis_id")
    private Long poseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private String startTime;

    private String endTime;

    private String duringTime;

    @Builder
    public PoseAnalysis(Interview interview, String startTime, String endTime, String duringTime) {
        this.interview = interview;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duringTime = duringTime;
    }

}
