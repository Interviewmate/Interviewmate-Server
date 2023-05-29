package org.interviewmate.domain.analysis.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataVO {

    private Double startSec;

    private Double endSec;

    private Double duringSec;

}
