package org.interviewmate.domain.analysis.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.interview.model.Interview;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BehaviorAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_analysis_id")
    private Long beId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private Long start;

    private Long end;

    private Long duringTIme;

    @Builder
    public BehaviorAnalysis(Interview interview, Long start, Long end, Long duringTIme) {
        this.interview = interview;
        this.start = start;
        this.end = end;
        this.duringTIme = duringTIme;
    }

}
