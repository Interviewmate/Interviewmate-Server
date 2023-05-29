package org.interviewmate.domain.interview.model;

import static org.interviewmate.domain.interview.model.AnalysisStatus.*;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.user.model.User;
import org.interviewmate.global.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "interview")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long score;

    private Long gazeScore;

    private Long poseScore;

    private Double videoDuration;

    @Enumerated(value = EnumType.STRING)
    private AnalysisStatus analysisStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gaze_analysis_id")
    private GazeAnalysis gazeAnalysis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pose_analysis_id")
    private PoseAnalysis poseAnalysis;

    @Builder
    public Interview(User user, GazeAnalysis gazeAnalysis, PoseAnalysis poseAnalysis) {
        this.user = user;
        this.gazeAnalysis = gazeAnalysis;
        this.poseAnalysis = poseAnalysis;
        this.analysisStatus = NOT_DONE;
    }

    public void setScore(double gazeScore, double poseScore) {
        this.gazeScore = Math.round(gazeScore);
        this.poseScore = Math.round(poseScore);
        this.score = Math.round((gazeScore + poseScore) / 2);
    }

    public void setVideoDuration(Double videoDuration) {
        this.videoDuration = videoDuration;
    }

    public void setAnalysisStatus(AnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }
}
