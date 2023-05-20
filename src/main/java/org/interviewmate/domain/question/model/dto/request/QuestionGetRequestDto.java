package org.interviewmate.domain.question.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Schema(description = "면접 질문 추천 request dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionGetRequestDto {
    @Schema(description = "사용자 id", example = "1")
    private Long userId;
    @Schema(description = "질문 개수", example = "10")
    private Integer questionNum;
    @Schema(description = "사용자가 선택한 cs 키워드 list", example = "ALGORITHM,NETWORK")
    private List<String> csKeyword;
}
