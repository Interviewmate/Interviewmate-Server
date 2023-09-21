package org.interviewmate.domain.interview.model.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewVideoVo {

    private String url;
    private String question;
    @Builder
    public InterviewVideoVo(String url, String question) {
        this.url = url;
        this.question = question;
    }
}
