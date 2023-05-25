package org.interviewmate.domain.interview.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.analysis.model.PoseAnalysis;
import org.interviewmate.domain.analysis.model.GazeAnalysis;
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

    private String videoDuration;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<PoseAnalysis> poseAnalysis;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<GazeAnalysis> gazeAnalyses;

    @Builder
    public Interview(User user) {
        this.user = user;
    }

    public void setPoseAnalysis(List<PoseAnalysis> poseAnalysis) {
        this.poseAnalysis = poseAnalysis;
    }

    public void setGazeAnalysis(List<GazeAnalysis> gazeAnalyses) {
        this.gazeAnalyses = gazeAnalyses;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

}
