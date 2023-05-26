package org.interviewmate.domain.analysis.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import org.interviewmate.domain.interview.model.Interview;
import org.interviewmate.global.common.BaseEntity;

@Entity
@Getter
public class PoseAnalysis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poseAnalysisId;

    @OneToOne(mappedBy = "poseAnalysis", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Interview interview;

    @OneToMany(mappedBy = "poseAnalysis",  cascade = CascadeType.ALL,  orphanRemoval = true)
    List<PoseAnalysisData> poseAnalysisData;

    @Builder
    public PoseAnalysis() {
    }

    public void setPoseAnalysisData(List<PoseAnalysisData> poseAnalysisData) {
        this.poseAnalysisData = poseAnalysisData;
    }

}
