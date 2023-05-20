package org.interviewmate.domain.question.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.interviewmate.domain.question.model.Question;

@Schema(description = "질문 정보 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionInfoDto {
    @Schema(description = "question id", example = "10")
    private Long questionId;
    @Schema(description = "질문 내용", example = " Big-O, Big-Theta, Big-Omega 에 대해 설명해 주세요.")
    private String content;

    @Schema(description = "질문 키워드", example = "Frontend")
    private String keyword;

    public QuestionInfoDto(Question question) {
        this.questionId = question.getQuestionId();
        this.content = question.getQuestion();
        this.keyword = question.getKeyword();
    }
}
