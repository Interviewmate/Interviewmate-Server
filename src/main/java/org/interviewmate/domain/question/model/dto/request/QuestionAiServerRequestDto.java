package org.interviewmate.domain.question.model.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionAiServerRequestDto {
    private Integer questionNum;
    private List<String> userKeyword;
    private List<String> portfolioKeyword;
    private String job;

    @Builder
    public QuestionAiServerRequestDto(Integer questionNum, List<String> userKeyword, List<String> portfolioKeyword, String job) {
        this.questionNum = questionNum;
        this.userKeyword = userKeyword;
        this.portfolioKeyword = portfolioKeyword;
        this.job = job;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }
}
