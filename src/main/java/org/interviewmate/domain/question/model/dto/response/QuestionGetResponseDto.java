package org.interviewmate.domain.question.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Schema(description = "질문 추천 response dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionGetResponseDto {
    @Schema(description = "반환된 질문 개수", example = "10")
    private Integer questionNum;
    @Schema(description = "질문 list", example = "[{\"questionId\":\"3\", \"content\":\"질문내용\", \"keyword\":\"질문 키워드\"}]")
    private List<QuestionInfoDto> questionList;
}
