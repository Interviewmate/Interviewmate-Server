package org.interviewmate.domain.analysis.model;

import java.util.ArrayList;
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
public class GazeAnalysis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gazeAnalysisId;

    @OneToOne(mappedBy = "gazeAnalysis", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Interview interview;

    @OneToMany(mappedBy = "gazeAnalysis",  cascade = CascadeType.ALL,  orphanRemoval = true)
    List<GazeAnalysisData> gazeAnalysisData = new ArrayList<>();

    @Builder
    public GazeAnalysis() {
    }

    public void setGazeAnalysisData(List<GazeAnalysisData> gazeAnalysisData) {
       gazeAnalysisData.clear();
       getGazeAnalysisData().addAll(gazeAnalysisData);
    }

}

