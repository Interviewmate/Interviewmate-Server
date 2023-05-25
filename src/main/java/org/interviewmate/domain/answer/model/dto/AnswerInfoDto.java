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
    @Schema(description = "답변", example = "존경하는 선배님과 동료들 앞에서 제 자신에게 좀 말해 주고 싶네요 연 진희로 사느라 너무 고생했고 너도 충분히 잘 해내고 있다고 멋지다 연진아 나 상 받았어 연진아 나 지금 되게 신나 마지막을 해 보고 싶었음")
    private String answer;
    @Schema(description = "답변 분석, 빈값이면(\"\") 없음", example = "면접자의 답변에서 여러 가지 잘한 점이 있었습니다. 먼저, 면접자는 자신에게 인정받는 순간이 작은 축복임을 인지하고 있어 보였습니다. 또한, 선배님과 동료들 앞에서 떨어지지 않고 자신감 있게 대답한 모습이 칭찬할 만합니다.\n" +
            " \n" +
            " 하지만 면접자의 답변이 질문과 연관성이 떨어지는 부분이 있었습니다. 면접 질문은 O(1)과 O(N^2)의 비교였는데, 면접자의 답변은 자기 자랑적인 내용이었습니다. 따라서 면접자분께서는 질문의 요점을 파악하고, 주어진 시간에 적절한 답변을 하는 능력을 더욱 갖추는 것이 필요합니다.\n" +
            " \n" +
            " 최종적으로 면접자분께서는 자신감과 긍정적인 태도를 잘 보여주셨지만, 추상적인 대답보다는 구체적으로 질문에 답하는 능력을 더욱 갖추시는 것이 필요하다는 점을 보완할 필요가 있습니다.")
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
