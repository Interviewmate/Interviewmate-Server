package org.interviewmate.domain.analysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.Interview;
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

    private Long start;

    private Long end;

    private Long duringTIme;

    @Builder
    public GazeAnalysis(Interview interview, Long start, Long end, Long duringTIme) {
        this.interview = interview;
        this.start = start;
        this.end = end;
        this.duringTIme = duringTIme;
    }

}
