package org.interviewmate.domain.behavior.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "behavior_analysis")
@Getter
public class BehaviorAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_analysis_id")
    private Long beId;

    @Column(columnDefinition = "TEXT")
    private String emotion;
    @Column(columnDefinition = "TEXT")
    private String pose;

    @Builder
    public BehaviorAnalysis(String emotion, String pose) {
        this.emotion = emotion;
        this.pose = pose;
    }

    public BehaviorAnalysis() { //처음에 빈 값 넣어줌
        this.emotion = "";
        this.pose = "";
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }
}
