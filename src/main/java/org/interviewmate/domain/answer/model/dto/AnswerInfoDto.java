package org.interviewmate.domain.answer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.answer.model.Answer;

@Schema(description = "답변 정보 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerInfoDto {
    @Schema(description = "질문", example = " 값이 주어졌을 때, 어떻게 하면 충돌이 최대한 적은 해시 함수를 설계할 수 있을까요?")
    private String question;
    @Schema(description = "답변", example = "10")
    private String answer;
    @Schema(description = "답변 분석, 빈값이면(\"\") 없음", example = "10")
    private String answerAnalysis;
    @Schema(description = "모범 답안", example = "해시 함수를 최적화하는 방법 중 하나는 충돌의 수를 최소화하는 것입니다.")
    private String bestAnswer;
    @Schema(description = "키워드", example = "Data Structure")
    private String keyword;

    public AnswerInfoDto(Answer answer) {
        this.question = answer.getQuestion().getQuestion();
        this.answer = answer.getContent();
        this.answerAnalysis = answer.getAnswerAnalysis();
        this.bestAnswer = answer.getQuestion().getBestAnswer();
        this.keyword = answer.getQuestion().getKeyword();
    }
}
