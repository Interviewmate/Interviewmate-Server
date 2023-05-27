package org.interviewmate.domain.analysis.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class keywordDistribution {

    private String name;
    private Long count;

    @Builder
    public keywordDistribution(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public void count() {
        this.count++;
    }

}
